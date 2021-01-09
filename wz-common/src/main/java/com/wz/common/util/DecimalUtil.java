package com.wz.common.util;

import java.math.BigDecimal;

/**
 * @projectName: wz-component
 * @package: com.wz.common.util
 * @className: DecimalUtil
 * @description: BigDecimal 工具类
 * @author: Zhi
 * @date: 2020-03-03 12:48
 * @version: 1.0
 */
public final class DecimalUtil {

    private DecimalUtil() {
    }

    public static BigDecimal add(double v1, double v2) {
        return new BigDecimal(Double.toString(v1))
                .add(new BigDecimal(Double.toString(v2)));
    }

    public static BigDecimal sub(double v1, double v2) {
        return new BigDecimal(Double.toString(v1))
                .subtract(new BigDecimal(Double.toString(v2)));
    }

}
