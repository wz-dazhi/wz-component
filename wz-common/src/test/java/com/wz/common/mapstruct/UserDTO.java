package com.wz.common.mapstruct;

import lombok.Data;

/**
 * @projectName: wz-component
 * @package: com.wz.common.mapstruct
 * @className: UserDTO
 * @description:
 * @author: zhi
 * @date: 2022/6/7
 * @version: 1.0
 */
@Data
public class UserDTO {
    private String uid;
    private String username;
    private String password;
    private Integer age;
    private String address;
    private Boolean flag;
    private String createTime;
    private String updateTime;
    private Double price;
}
