package com.wz.excel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.wz.common.util.StringUtil;
import com.wz.excel.enums.TemplateTypeEnum;
import com.wz.excel.exception.ExcelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.util
 * @className: ExcelUtil
 * @description:
 * @author: zhi
 * @date: 2021/6/17
 * @version: 1.0
 */
@Slf4j
public final class ExcelUtil {

    private ExcelUtil() {
    }

    public static ExcelWriter writer(OutputStream os, ExcelTypeEnum excelType, InputStream templateInputStream) {
        return EasyExcel.write(os)
                .autoCloseStream(true)
                .excelType(excelType)
                .withTemplate(templateInputStream)
                .build();
    }

    public static void doSimpleWrite(ExcelWriter writer, Integer sheetNo, String sheetName, Class<?> head, List data) {
        final WriteSheet sheet = EasyExcel.writerSheet(sheetNo, sheetName)
                .head(head)
                .build();
        writer.write(data, sheet);
    }

    public static void doSimpleFill(ExcelWriter writer, Integer sheetNo, String sheetName, Class<?> head, List data) {
        final WriteSheet sheet = EasyExcel.writerSheet(sheetNo, sheetName)
                .head(head)
                .build();
        writer.fill(data, sheet);
    }

    public static void finish(ExcelWriter writer) {
        if (null != writer) {
            writer.finish();
        }
    }

    public static InputStream getTemplate(TemplateTypeEnum templateType, String templatePath) {
        if (null == templateType || StringUtil.isBlank(templatePath)) {
            return null;
        }
        switch (templateType) {
            case NONE:
                return null;
            case CLASSPATH:
                return getClassPathTemplate(templatePath);
            case LOCAL:
                return getLocalTemplate(templatePath);
            case URL:
                return getUrlTemplate(templatePath);
            default:
                final String msg = "模板类型不正确: " + templateType;
                log.error(msg);
                throw new ExcelException(msg);
        }
    }

    public static InputStream getClassPathTemplate(String templatePath) {
        try {
            return new ClassPathResource(templatePath).getInputStream();
        } catch (IOException e) {
            throw notFoundTemplate(templatePath, e);
        }
    }

    public static InputStream getLocalTemplate(String templatePath) {
        try {
            return new FileInputStream(templatePath);
        } catch (FileNotFoundException e) {
            throw notFoundTemplate(templatePath, e);
        }
    }

    public static InputStream getUrlTemplate(String templatePath) {
        try {
            return new URL(templatePath).openStream();
        } catch (IOException e) {
            throw notFoundTemplate(templatePath, e);
        }
    }

    private static ExcelException notFoundTemplate(String templatePath, Exception e) {
        final String msg = String.format("没有找到对应的模板文件: %s", templatePath);
        log.error(msg, e);
        return new ExcelException(msg);
    }
}
