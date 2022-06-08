package com.wz.common.mapstruct.condition;

import lombok.experimental.UtilityClass;
import org.mapstruct.Condition;

/**
 * @projectName: wz-component
 * @package: com.wz.common.mapstruct
 * @className: ObjectCondition
 * @description:
 * @author: zhi
 * @date: 2022/6/7
 * @version: 1.0
 */
@UtilityClass
public class ObjectCondition {

    /**
     * 判断不能为空
     *
     * @param o 判断对象
     * @return true: 不为空, false: 空
     */
    @Condition
    public static boolean isNotNull(Object o) {
        if (o != null) {
            if (o instanceof String) {
                return !((String) o).isBlank();
            }
            return true;
        }
        return false;
    }

}
