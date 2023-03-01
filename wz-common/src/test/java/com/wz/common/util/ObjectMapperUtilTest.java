package com.wz.common.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ObjectMapperUtilTest {

    @Test
    void toBytes() {
        Bean b = new Bean("zhangsan", 18, BigDecimal.ONE, new Date(), LocalDateTime.now(), false, 1.1D, 1.2D, 2L, 2L, 3, 3, Bean.T.T1);
        byte[] bytes = ObjectMapperUtil.toBytes(b);
        System.out.println(Arrays.toString(bytes));
    }

    @Test
    void toBean() {
        Bean b = new Bean("zhangsan", 18, BigDecimal.ONE, new Date(), LocalDateTime.now(), false, 1.1D, 1.2D, 2L, 2L, 3, 3, Bean.T.T1);
        byte[] bytes = ObjectMapperUtil.toBytes(b);
        Bean b2 = ObjectMapperUtil.toBean(bytes, Bean.class);
        System.out.println(b2);
    }
}