package com.wz.common.mapstruct;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @projectName: wz-component
 * @package: com.wz.common.mapstruct
 * @className: User
 * @description:
 * @author: zhi
 * @date: 2022/6/7
 * @version: 1.0
 */
@Data
public class User {
    private String uid;
    private String username;
    private String password;
    private int age;
    private Address address;
    private boolean flag;
    private LocalDateTime createTime;
    private Date updateTime;
    private BigDecimal price;

    @Data
    public static class Address {
        private String prov;
        private String city;
        private String coty;

        @Override
        public String toString() {
            return prov + '-' + city + '-' + coty;
        }
    }
}
