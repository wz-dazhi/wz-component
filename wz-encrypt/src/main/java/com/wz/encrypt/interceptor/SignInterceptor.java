package com.wz.encrypt.interceptor;

import com.wz.common.exception.SystemException;
import com.wz.common.util.JsonUtil;
import com.wz.common.util.MapUtil;
import com.wz.common.util.StringUtil;
import com.wz.encrypt.algorithm.SignAlgorithm;
import com.wz.encrypt.annotation.Sign;
import com.wz.encrypt.auto.EncryptProperties;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import static com.wz.encrypt.enums.EncryptEnum.*;

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

    private static final String SIGN = "sign";
    private static final String SIGN_TIME = "timestamp";

    private final SignAlgorithm signAlgorithm;
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
        resp.setCharacterEncoding("UTF-8");
        // 校验sign
        Map<String, Object> map = this.verifySign(req);

        // 校验参数是否被篡改
        this.verifyParams(map, req);
        return true;
    }

    private Map<String, Object> verifySign(HttpServletRequest req) throws Exception {
        String sign = req.getHeader(SIGN);
        log.debug(">>> Request header sign: {}", sign);
        if (StringUtil.isBlank(sign)) {
            log.error("非法请求:缺少签名信息");
            throw new SystemException(NOT_EXIST_SIGNATURE);
        }
        // 签名算法
        String decryptSign = signAlgorithm.decrypt(sign, properties.getKey());
        log.debug(">>> Decrypt header sign: {}", decryptSign);
        Map<String, Object> map = JsonUtil.toHashMap(decryptSign, String.class, Object.class);
        if (MapUtil.isEmpty(map)) {
            throw new SystemException(NOT_EXIST_SIGNATURE);
        }
        if (null == map.get(SIGN_TIME)) {
            throw new SystemException(NOT_EXIST_SIGNATURE_TIME);
        }
        long currentTime = System.currentTimeMillis();
        long signTime = (long) map.get(SIGN_TIME);
        long time = currentTime - signTime;
        log.debug(">>> Sign verify currentTime: {}, signTime: {}, diff: {}", currentTime, signTime, time);
        // 签名时间和服务器时间相差 ? 分钟以上则认为是过期请求，此时间可以配置
        if (time > properties.getSignExpireTime() * 60000) {
            throw new SystemException(REQUEST_EXPIRED);
        }
        return map;
    }

    private void verifyParams(Map<String, Object> map, HttpServletRequest req) throws SystemException {
        map.remove(SIGN_TIME);
        // GET请求处理参数
        if (HttpMethod.GET.name().equals(req.getMethod())) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                this.doVerifyParams(entry, String.valueOf(req.getParameter(entry.getKey())));
            }
        } else {
            // POST PUT DELETE 处理Body
            try {
                @Cleanup final BufferedReader reader = req.getReader();
                final String body = reader.lines()
                        .reduce(String::concat)
                        .orElseThrow(SystemException::new);
                Map<String, Object> bodyMap = JsonUtil.toHashMap(body, String.class, Object.class);
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    this.doVerifyParams(entry, String.valueOf(bodyMap.get(entry.getKey())));
                }
            } catch (IOException e) {
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
