package com.wz.webmvc.controller;

import com.wz.common.exception.BusinessException;
import com.wz.datasource.common.mybatisplus.model.Page;
import com.wz.swagger.model.Result;
import com.wz.swagger.util.R;
import com.wz.webmvc.annotation.NoResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/name")
    public Obj name(@NotBlank(message = "姓名不能为空") String name) {
        return o(name);
    }

    @GetMapping("/string")
    public Result<String> string() {
        return R.ok("string");
    }

    @GetMapping("/obj")
    public Obj obj() {
        return o("obj");

    }

    @GetMapping("/obj2")
    public Result<Obj> obj2() {
        return R.ok(o("obj2"));
    }

    @NoResult
    @GetMapping("/obj3")
    public Obj obj3() {
        return o("obj3");
    }

    @GetMapping("/page")
    public Page<Obj> page() {
        return new Page<>();
    }

    @GetMapping("/page2")
    public Result<Page<Obj>> page2() {
        return R.ok(new Page<>());
    }

    private Obj o(String name) {
        Obj o = new Obj();
        o.setName(name);
        o.setPassword("123456");
        o.setAge(20);
        return o;
    }

    @ApiModel("obj对象")
    static class Obj {
        @ApiModelProperty("姓名")
        private String name;

        @ApiModelProperty("密码")
        private String password;
        @ApiModelProperty("年龄")
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
