package com.wz.excel.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.wz.common.util.StringUtil;
import com.wz.excel.enums.TemplateTypeEnum;
import com.wz.excel.exception.ExcelException;
import com.wz.excel.model.ReadSheetWrapper;
import com.wz.excel.read.handler.DefaultReadHandler;
import com.wz.excel.read.handler.ReadHandler;
import com.wz.excel.read.handler.ValidatorReadHandler;
import com.wz.excel.read.service.ReadService;
import com.wz.excel.read.service.ValidatorReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
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

    public static InputStream urlToInputStream(URL url) {
        try {
            return url.openStream();
        } catch (IOException e) {
            log.error("没有找到对应的文件: [{}]-[{}]-[{}], e: ", url.getHost(), url.getHost(), url.getPath());
            throw new ExcelException("没有找到对应的文件");
        }
    }

    //------------------------------read------------------------------------------

    public static <T> List<T> doReadAllSync(String filePath, Class<T> head) {
        return doReadAllSync(filePath, head, null);
    }

    public static <T> List<T> doReadAllSync(File file, Class<T> head) {
        return doReadAllSync(file, head, null);
    }

    public static <T> List<T> doReadAllSync(URL url, Class<T> head) {
        return doReadAllSync(url, head, null);
    }

    public static <T> List<T> doReadAllSync(InputStream is, Class<T> head) {
        return doReadAllSync(is, head, null);
    }

    public static <T> List<T> doReadAllSync(String filePath, Class<T> head, ReadListener<T> readListener) {
        return EasyExcel.read(filePath, head, readListener).doReadAllSync();
    }

    public static <T> List<T> doReadAllSync(File file, Class<T> head, ReadListener<T> readListener) {
        return EasyExcel.read(file, head, readListener).doReadAllSync();
    }

    public static <T> List<T> doReadAllSync(URL url, Class<T> head, ReadListener<T> readListener) {
        InputStream is = urlToInputStream(url);
        return doReadAllSync(is, head, readListener);
    }

    public static <T> List<T> doReadAllSync(InputStream is, Class<T> head, ReadListener<T> readListener) {
        return EasyExcel.read(is, head, readListener).doReadAllSync();
    }

    public static <T> void doRead(String filePath, Class<T> head, ReadListener<T> readListener) {
        EasyExcel.read(filePath, head, readListener).sheet().doRead();
    }

    public static <T> void doRead(File file, Class<T> head, ReadListener<T> readListener) {
        EasyExcel.read(file, head, readListener).sheet().doRead();
    }

    public static <T> void doRead(URL url, Class<T> head, ReadListener<T> readListener) {
        InputStream is = urlToInputStream(url);
        doRead(is, head, readListener);
    }

    public static <T> void doRead(InputStream is, Class<T> head, ReadListener<T> readListener) {
        EasyExcel.read(is, head, readListener).sheet().doRead();
    }

    public static <T> void doReadSheetNo(InputStream is, Integer sheetNo, Class<T> head, ReadListener<T> readListener) {
        EasyExcel.read(is, head, readListener).sheet(sheetNo).doRead();
    }

    public static <T> DefaultReadHandler<T> doReadService(InputStream is, Class<T> head, ReadService<T> readService) {
        DefaultReadHandler<T> defaultReadHandler = new DefaultReadHandler<>(readService);
        doReadHandler(is, head, defaultReadHandler);
        return defaultReadHandler;
    }

    public static <T> ValidatorReadHandler<T> doValidatorReadService(InputStream is, Class<T> head, ValidatorReadService<T> validatorReadService) {
        ValidatorReadHandler<T> validatorReadHandler = new ValidatorReadHandler<>(validatorReadService);
        doValidatorReadHandler(is, head, validatorReadHandler);
        return validatorReadHandler;
    }

    public static <T> void doReadHandler(InputStream is, Class<T> head, DefaultReadHandler<T> defaultReadHandler) {
        doRead(is, head, defaultReadHandler);
    }

    public static <T> void doValidatorReadHandler(InputStream is, Class<T> head, ValidatorReadHandler<T> validatorReadHandler) {
        doRead(is, head, validatorReadHandler);
    }

    public static <T> void doReadHandler(InputStream is, Class<T> head, ReadHandler<T> readHandler) {
        doRead(is, head, readHandler);
    }

    public static <T> void doReadSingleSheetNo(InputStream is, ReadSheetWrapper<T> wrapper) {
        doReadSingleSheetNo(is, wrapper.getSheetNo(), wrapper.getHead(), wrapper.getReadListener());
    }

    public static <T> void doReadSingleSheetNo(InputStream is, Integer sheetNo, Class<T> head, ReadListener<T> readListener) {
        ExcelReader reader = reader(is);
        ReadSheet sheet = readSheetNo(sheetNo, head, readListener);
        reader.read(sheet);
        finish(reader);
    }

    public static void doReadMultiSheetNo(InputStream is, ReadSheetWrapper<?>... wrapper) {
        if (null == wrapper || wrapper.length <= 0) {
            throw new IllegalArgumentException("sheet cannot is null.");
        }
        ExcelReader reader = reader(is);
        ReadSheet[] readSheets = new ReadSheet[wrapper.length];
        for (int i = 0; i < wrapper.length; i++) {
            ReadSheetWrapper<?> w = wrapper[i];
            ReadSheet sheet = readSheetNo(w.getSheetNo(), w.getHead(), w.getReadListener());
            readSheets[i] = sheet;
        }
        reader.read(readSheets);
        finish(reader);
    }

    public static <T> ExcelReader reader(InputStream is) {
        return EasyExcel.read(is).build();
    }

    public static <T> ExcelReader reader(InputStream is, Class<T> head, ReadListener<T> readListener) {
        return EasyExcel.read(is, head, readListener).build();
    }

    public static <T> ReadSheet readSheetNo(Integer sheetNo, Class<?> head, ReadListener<?> readListener) {
        return EasyExcel.readSheet(sheetNo).head(head).registerReadListener(readListener).build();
    }

    private static void finish(ExcelReader reader) {
        if (null != reader) {
            reader.finish();
        }
    }
}
