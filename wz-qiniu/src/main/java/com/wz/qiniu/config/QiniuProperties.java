package com.wz.qiniu.config;

import com.wz.qiniu.enums.RegionEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @projectName: wz-component
 * @package: com.wz.qiniu.config
 * @className: QiniuProperties
 * @description:
 * @author: Zhi
 * @date: 2020-03-08 15:08
 * @version: 1.0
 */
@Data
@ConfigurationProperties(prefix = "qiniu.config")
public class QiniuProperties {
    /**
     * Access
     */
    private String accessKey;
    /**
     * Secret Key
     */
    private String secretKey;
    /**
     * 空间名称
     */
    private String bucket;
    /**
     * url 地址
     */
    private String url;
    /**
     * url base path 地址
     */
    private String urlBasePath;
    /**
     * 地区,默认华北
     */
    private RegionEnum region = RegionEnum.HUABEI;
}
