package com.wz.webmvc.controller;

import com.wz.common.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotBlank;

/**
 * @projectName: wz-component
 * @package: com.wz.webmvc.controller
 * @className: ControllerTest
 * @description:
 * @author: zhi
 * @date: 2021/10/25
 * @version: 1.0
 */
@Validated
@Controller
public class ControllerTest {

    @GetMapping("/normal")
    public void hello() {
    }

    @GetMapping("/exception")
    public void exception() {
        throw new BusinessException("发生了业务异常.");
    }

    @GetMapping("/unauthorized")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String unauthorized() {
        // 跳转到templates/401.html
        return "401";
    }

    @GetMapping("/forbidden")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String forbidden() {
        // 跳转到templates/403.html
        return "403";
    }

    @GetMapping("/name")
    public String name(@NotBlank(message = "姓名不能为空") String name) {
        System.out.println("/name=" + name);
        return "/normal";
    }

}
