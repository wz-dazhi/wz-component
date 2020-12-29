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
@Plugin(name = "LinePatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"ll", "logLine"})
public final class LinePatternConverter extends LogEventPatternConverter implements LocationAware {
    private static final LinePatternConverter INSTANCE = new LinePatternConverter();

    private LinePatternConverter() {
        super("logLine", "logLine");
    }

    public static LinePatternConverter newInstance(final String[] options) {
        return INSTANCE;
    }

    @Override
    public void format(final LogEvent event, final StringBuilder output) {
        final StackTraceElement element = StackTraceUtil.findCaller(event.getSource());

        if (Objects.nonNull(element)) {
            output.append(element.getLineNumber());
        }
    }

    @Override
    public boolean requiresLocation() {
        return true;
    }

}
