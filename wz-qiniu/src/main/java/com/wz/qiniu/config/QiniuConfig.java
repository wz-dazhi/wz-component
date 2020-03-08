package com.wz.qiniu.config;

import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: wz-component
 * @package: com.wz.qiniu.config
 * @className: QiniuConfig
 * @description:
 * @author: Zhi
 * @date: 2020-03-08 15:18
 * @version: 1.0
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(QiniuProperties.class)
public class QiniuConfig {
    private final QiniuProperties qiNiuProperties;

    @Bean
    public Auth auth() {
        return Auth.create(qiNiuProperties.getAccessKey(), qiNiuProperties.getSecretKey());
    }

    @Bean
    public UploadManager uploadManager() {
        Region region;
        switch (qiNiuProperties.getRegion()) {
            case HUADONG:
                region = Region.region0();
                break;
            case HUANAN:
                region = Region.region2();
                break;
            case BEIMEI:
                region = Region.regionNa0();
                break;
            case XINJIAPO:
                region = Region.regionAs0();
                break;
            case HUABEI:
            default:
                region = Region.region1();
                break;
        }
        com.qiniu.storage.Configuration cfg = new com.qiniu.storage.Configuration(region);
        return new UploadManager(cfg);
    }

}
