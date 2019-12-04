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
     * 加解密key
     */
    private String key;

    /**
     * 是否开启调试模式
     */
    private boolean debug = true;

    /**
     * 签名过期时间（分钟)
     */
    private long signExpireTime = 10L;

    /**
     * 字符集
     */
    private String charset = "UTF-8";

    /**
     * 签名开关
     */
    private boolean signEnable;

    /**
     * 校验签名的controller
     */
    private String[] signPath = new String[]{};

}
