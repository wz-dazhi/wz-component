package com.wz.encrypt.auto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @projectName: wz
 * @package: com.wz.encrypt.auto
 * @className: EncryptProperties
 * @description: 接口加解密配置
 * @author: Zhi Wang
 * @date: 2019/3/5 11:45 AM
 * @version: 1.0
 **/
@Data
@ConfigurationProperties(prefix = "api.encrypt")
public class EncryptProperties {

    /**
     * 加密, 是否开启调试模式
     */
    private boolean debug = false;

    /**
     * 加解密key
     */
    private String key;

    /**
     * 签名开关
     */
    private boolean signEnable = true;

    /**
     * 签名key, 可以跟加密key一致
     */
    private String signKey;

    /**
     * 签名过期时间（分钟)
     */
    private long signExpireTime = 10L;

    /**
     * 校验签名的controller
     */
    private String[] signPath = new String[]{};

}
