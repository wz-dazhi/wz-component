package com.wz.logging.log4j2.pattern;

import com.wz.logging.log4j2.util.StackTraceUtil;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.impl.LocationAware;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;

import java.util.Objects;

/**
 * TODO 此方法会存在性能问题, 且异步log获取不到线程调用栈
 *
 * @author: zhi
 * @date: 2020-12-29 14:20:00
 */
@Plugin(name = "MethodPatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"lm", "logMethod"})
public final class MethodPatternConverter extends LogEventPatternConverter implements LocationAware {

    private static final MethodPatternConverter INSTANCE = new MethodPatternConverter();

    private MethodPatternConverter() {
        super("logMethod", "logMethod");
    }

    public static MethodPatternConverter newInstance(final String[] options) {
        return INSTANCE;
    }

    @Override
    public void format(final LogEvent event, final StringBuilder toAppendTo) {
        final StackTraceElement element = StackTraceUtil.findCaller(event.getSource());

        if (Objects.nonNull(element)) {
            toAppendTo.append(element.getMethodName());
        }
    }

    @Override
    public boolean requiresLocation() {
        return true;
    }

}
