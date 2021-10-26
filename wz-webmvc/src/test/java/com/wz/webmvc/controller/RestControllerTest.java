package com.wz.webmvc.controller;

import com.wz.common.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: wz-component
 * @package: com.wz.webmvc.controller
 * @className: ControllerTest
 * @description:
 * @author: zhi
 * @date: 2021/10/25
 * @version: 1.0
 */
@RestController
@RequestMapping("/rest")
public class RestControllerTest {

    @GetMapping("/normal")
    public String hello() {
        return "normal";
    }

    @GetMapping("/exception")
    public void exception() {
        throw new BusinessException("发生了业务异常.");
    }

    @GetMapping("/unauthorized")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String unauthorized() {
        return "unauthorized - 401";
    }

    @GetMapping("/forbidden")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String forbidden() {
        return "forbidden - 403";
    }

}
