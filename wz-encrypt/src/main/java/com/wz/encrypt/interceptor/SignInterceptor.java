package com.wz.encrypt.interceptor;

import com.wz.common.exception.SystemException;
import com.wz.common.util.JsonUtil;
import com.wz.common.util.MapUtil;
import com.wz.common.util.StringUtil;
import com.wz.encrypt.algorithm.EncryptAlgorithm;
import com.wz.encrypt.algorithm.SignAlgorithm;
import com.wz.encrypt.annotation.Decrypt;
import com.wz.encrypt.annotation.Sign;
import com.wz.encrypt.auto.EncryptProperties;
import com.wz.encrypt.constant.EncryptConsts;
import com.wz.webmvc.wrapper.RequestBodyWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

import static com.wz.encrypt.enums.EncryptEnum.NOT_EXIST_SIGNATURE;
import static com.wz.encrypt.enums.EncryptEnum.NOT_EXIST_SIGNATURE_TIME;
import static com.wz.encrypt.enums.EncryptEnum.PARAMETER_TAMPERED;
import static com.wz.encrypt.enums.EncryptEnum.REQUEST_EXPIRED;

/**
 * @projectName: wz
 * @package: com.wz.common.interceptor
 * @className: SignInterceptor
 * @description: 签名拦截器
 * @author: Zhi Wang
 * @date: 2019/3/1 5:25 PM
 * @version: 1.0
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class SignInterceptor implements HandlerInterceptor {
    private final SignAlgorithm signAlgorithm;
    private final EncryptAlgorithm encryptAlgorithm;
    private final EncryptProperties properties;

    /**
     * 进入controller方法之前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod) || HttpMethod.OPTIONS.name().equals(req.getMethod())) {
            return true;
        }
        HandlerMethod m = (HandlerMethod) handler;
        // 判断是否开启签名校验
        if (!properties.isSignEnable() || !m.hasMethodAnnotation(Sign.class)) {
            return true;
        }
        resp.setCharacterEncoding(EncryptConsts.CHARSET_UTF_8);
        // 校验sign
        Map<String, Object> map = this.verifySign(req);

        // 校验参数是否被篡改
        this.verifyParams(map, req, m);
        return true;
    }

    private Map<String, Object> verifySign(HttpServletRequest req) throws Exception {
        String sign = req.getHeader(EncryptConsts.SIGN);
        log.debug(">>> Request header sign: {}", sign);
        if (StringUtil.isBlank(sign)) {
            log.error("非法请求:缺少签名信息");
            throw new SystemException(NOT_EXIST_SIGNATURE);
        }
        // 签名算法
        String decryptSign = signAlgorithm.decrypt(sign, properties.getSignKey());
        log.debug(">>> Decrypt header sign: {}", decryptSign);
        Map<String, Object> map = JsonUtil.toHashMap(decryptSign, String.class, Object.class);
        if (MapUtil.isEmpty(map)) {
            throw new SystemException(NOT_EXIST_SIGNATURE);
        }
        if (null == map.get(EncryptConsts.SIGN_TIME)) {
            throw new SystemException(NOT_EXIST_SIGNATURE_TIME);
        }
        long currentTime = System.currentTimeMillis();
        long signTime = (long) map.get(EncryptConsts.SIGN_TIME);
        long time = currentTime - signTime;
        log.debug(">>> Sign verify currentTime: {}, signTime: {}, diff: {}", currentTime, signTime, time);
        // 签名时间和服务器时间相差 ? 分钟以上则认为是过期请求，此时间可以配置
        if (time > properties.getSignExpireTime() * 60000) {
            throw new SystemException(REQUEST_EXPIRED);
        }
        return map;
    }

    private void verifyParams(Map<String, Object> map, HttpServletRequest req, HandlerMethod m) throws SystemException {
        map.remove(EncryptConsts.SIGN_TIME);
        // GET请求处理参数
        if (HttpMethod.GET.name().equals(req.getMethod())) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                this.doVerifyParams(entry, String.valueOf(req.getParameter(entry.getKey())));
            }
        } else {
            // POST PUT DELETE 处理Body
            try {
                // 这里只能使用request包装类, 因为req.getReader()  req.getInputStream() 只能读取一次.
                if (!(req instanceof RequestBodyWrapper)) {
                    throw new SystemException();
                }
                String body = ((RequestBodyWrapper) req).getBody();
                Map<String, Object> bodyMap;
                // 加密数据, 需要先进行解密
                if (Objects.nonNull(m.getMethodAnnotation(Decrypt.class)) && !properties.isDebug()) {
                    String decryptBody = encryptAlgorithm.decrypt(body, properties.getKey());
                    bodyMap = JsonUtil.toHashMap(decryptBody, String.class, Object.class);
                } else {
                    bodyMap = JsonUtil.toHashMap(body, String.class, Object.class);
                }
                bodyMap.remove(EncryptConsts.SIGN_TIME);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    this.doVerifyParams(entry, String.valueOf(bodyMap.get(entry.getKey())));
                }
            } catch (SystemException e) {
                throw e;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new SystemException();
            }
        }
    }

    private void doVerifyParams(Map.Entry<String, Object> entry, String reqValue) throws SystemException {
        Object value = entry.getValue();
        String signValue = String.valueOf(value);
        if (!signValue.equals(reqValue)) {
            log.error(">>> 非法请求:参数被篡改. key: {}, signValue: {}, reqValue: {}", entry.getKey(), signValue, reqValue);
            throw new SystemException(PARAMETER_TAMPERED);
        }
    }

}
