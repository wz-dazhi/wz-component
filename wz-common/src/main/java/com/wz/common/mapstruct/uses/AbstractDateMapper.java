package com.wz.common.mapstruct.uses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Supplier;

/**
 * @projectName: wz-component
 * @package: com.wz.common.mapstruct.uses
 * @className: AbstractDateMapper
 * @description:
 * @author: zhi
 * @date: 2022/6/8
 * @version: 1.0
 */
public abstract class AbstractDateMapper {

    private final ThreadLocal<SimpleDateFormat> formatHolder = ThreadLocal.withInitial(withInitial());

    protected abstract SimpleDateFormat createSimpleDateFormat();

    protected Supplier<SimpleDateFormat> withInitial() {
        return this::createSimpleDateFormat;
    }

    public String asString(Date date) {
        return date != null ? get().format(date) : null;
    }

    public Date asDate(String date) {
        if (null == date) {
            return null;
        }
        try {
            return get().parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public SimpleDateFormat get() {
        return formatHolder.get();
    }

    public void remove() {
        formatHolder.remove();
    }

}
