package com.wz.common.util;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Maps;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.wz.common.constant.DateConsts;
import com.wz.common.model.JwtData;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * JWT 工具类
 */
@Slf4j
public final class JwtUtil {
    private JwtUtil() {
    }

    /**
     * 初始化head部分的数据为{"alg":"HS256", "type":"JWT"}
     */
    private static final JWSHeader HEADER = new JWSHeader.Builder(JWSAlgorithm.HS256)
            .type(JOSEObjectType.JWT)
            .build();

    /**
     * 生成token
     */
    public static String create(Map<String, Object> payload, String secret) {
        if (StringUtil.isBlank(secret)) {
            log.error("密钥为空. ");
            throw new RuntimeException("密钥为空, 签名失败.");
        }
        if (null == payload) {
            payload = Maps.newHashMap();
        }
        JWSObject jwsObject = new JWSObject(HEADER, new Payload(payload));
        try {
            jwsObject.sign(new MACSigner(secret));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("签名失败, e: ", e);
            throw new RuntimeException("签名失败.");
        }
    }

    /**
     * 校验token的合法性
     */
    public static <T> JwtData<T> verify(String token, String secret, Class<T> clazz, String expKey) {
        JwtData<T> j = new JwtData<>();
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            JWSVerifier verifier = new MACVerifier(secret);
            final boolean isSuccess = jwsObject.verify(verifier);
            j.setSuccess(isSuccess);
            if (isSuccess) {
                Payload payload = jwsObject.getPayload();
                // 校验过期时间
                final String expStr = String.valueOf(payload.toJSONObject().get(expKey));
                if (StringUtil.isBlank(expStr)) {
                    j.setSuccess(false);
                    return j;
                }
                final LocalDateTime exp = LocalDateTime.parse(expStr, DateConsts.DATE_TIME_HH_MM_SS_SSS_FORMATTER);
                if (exp.isBefore(LocalDateTime.now())) {
                    j.setSuccess(false);
                    return j;
                }
                j.setData(JsonUtil.toBean(payload.toString(), clazz));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return j;
    }

    public static void main(String[] args) {
        // Generate random 256-bit (32-byte) shared secret
//        SecureRandom random = new SecureRandom();
//        byte[] sharedSecret = new byte[32];
//        random.nextBytes(sharedSecret);
//        final String secret = new String(sharedSecret, StandardCharsets.UTF_8);
//        System.out.println(secret);
        // 随机生成密钥
        final String secret = RandomUtil.randomString(32);
        System.out.println(secret);
//
//        final User user = new User();
//        user.setUserNo("123456");
//        user.setUsername("张三");
//
//        final String token = JwtUtil.create(MapUtil.beanToMap(user), secret);
//        System.out.println(token);
//        final JwtData<User> j = JwtUtil.verify(token, secret, User.class);
//        System.out.println(j);
    }

}
