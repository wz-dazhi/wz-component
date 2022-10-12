package com.wz.common.util;

import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BeanCopierUtilTest {
    A a = new A();

    @BeforeEach
    void before() {
        a.setName("张三");
        a.setAge(20);
        a.setPrice(new BigDecimal("130"));
        a.setDate(new Date());
        a.setBirth(LocalDateTime.now());
        a.setB(false);
        a.setD(1.1D);
        a.setD2(2.2D);
        a.setL1(3L);
        a.setL2(4L);
//        a.setI1(5);
//        a.setI2(6);
    }

    @Test
    void copy() {
        System.out.println(a);
        long l = System.currentTimeMillis();
//        for (int i = 0; i < 10000000; i++) {
            B b = BeanCopierUtil.copy(a, B.class);
            System.out.println(b);
            A a2 = BeanCopierUtil.copy(b, A.class);
            System.out.println(a2);
//        }
        System.out.println("消耗: " + (System.currentTimeMillis() - l) / 1000);
    }
}

@Data
class A {
    private String name;
    private int age;
    private BigDecimal price;
    private Date date;
    private LocalDateTime birth;
    private boolean b;
    private Double d;
    private double d2;
    private Long l1;
    private long l2;
//    private Integer i1;
//    private int i2;
}

@Data
class B {
    private String name;
    private int age;
    private BigDecimal price;
    private Date date;
    private LocalDateTime birth;
    private boolean b;
    private Double d;
    private double d2;
//    private Long l1;
    private long l1;
    private long l2;
    private Integer i1;
    private int i2;
}