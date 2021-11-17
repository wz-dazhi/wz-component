package com.wz.excel.export.handler;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.wz.common.constant.DateConsts;
import com.wz.common.util.CollectionUtil;
import com.wz.common.util.StringUtil;
import com.wz.excel.annotation.Export;
import com.wz.excel.constant.ExcelConsts;
import com.wz.excel.enums.TemplateTypeEnum;
import com.wz.excel.exception.ExcelException;
import com.wz.excel.util.ExcelUtil;
import com.wz.webmvc.util.WebContextUtil;
import org.springframework.core.MethodParameter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.export.handler
 * @className: DefaultExportHandler
 * @description:
 * @author: zhi
 * @date: 2021/6/17
 * @version: 1.0
 */
public class DefaultExportHandler extends AbstractExportHandler implements ExportHandler {

    @Override
    protected void doExport(List list, MethodParameter parameter, Export export) {
        final HttpServletResponse resp = WebContextUtil.getResponse();
        final String fileName = getFileName(export.fileName());
        final ExcelTypeEnum excelType = export.suffix();
        String fullFileName;
        // 默认使用注解的后缀格式
        if (fileName.lastIndexOf('.') != -1) {
            fullFileName = fileName.substring(0, fileName.lastIndexOf('.')) + excelType.getValue();
        } else {
            fullFileName = fileName + excelType.getValue();
        }
        this.setResponseHeader(resp, fullFileName);
        final ServletOutputStream os;
        try {
            os = resp.getOutputStream();
        } catch (IOException e) {
            log.error("导出获取OutputStream异常, e: ", e);
            throw new ExcelException();
        }
        final ExcelWriter writer = ExcelUtil.writer(os, excelType, ExcelUtil.getTemplate(export.templateType(), export.template()));
        try {
            // 单sheet
            if (isSimpleSheet(parameter)) {
                this.simpleWrite(list, parameter, writer, export);
            } else if (isMultiSheet(parameter)) {
                // 多sheet
                this.multiWrite(list, parameter, writer, export);
            } else {
                log.error("Excel导出异常, 数据类型不支持.");
                throw new ExcelException();
            }
        } catch (Exception e) {
            if (e instanceof ExcelException) {
                throw e;
            }
            log.error("Excel导出异常. e: ", e);
            throw new ExcelException();
        }
    }

    protected void simpleWrite(List list, MethodParameter parameter, ExcelWriter writer, final Export export) {
        final Export.Sheet s = export.sheet()[0];
        final int sheetNo = 0;
        final String sheetName = this.getSheetName(s.sheetName(), sheetNo + 1);
        // 优先使用手动设置的head
        final Class<?> head = s.head() != Object.class ? s.head() : this.getDataClass(parameter);
        try {
            if (TemplateTypeEnum.NONE == export.templateType()) {
                ExcelUtil.doSimpleWrite(writer, sheetNo, sheetName, head, list);
            } else {
                ExcelUtil.doSimpleFill(writer, sheetNo, sheetName, head, list);
            }
        } finally {
            ExcelUtil.finish(writer);
        }
    }

    protected void multiWrite(List list, MethodParameter parameter, ExcelWriter writer, final Export export) {
        final boolean isNoneTemplate = TemplateTypeEnum.NONE == export.templateType();
        final int size = list.size();
        final Export.Sheet[] sheets = export.sheet();
        // 判断sheets 与 list不匹配的情况
        boolean sheetIsNull = (sheets == null || sheets.length == 0);
        try {
            for (int i = 0; i < size; i++) {
                final Object data = list.get(i);
                if (null == data || !List.class.isAssignableFrom(data.getClass())) {
                    throw new ExcelException("数据类型错误, 请检查");
                }
                final List sheetData = (List) data;
                // 1. list不为空, 取list的泛型, 负责取手动设置的head
                Class<?> head = CollectionUtil.isNotEmpty(sheetData) ? sheetData.get(0).getClass() : Object.class;
                String sheetName = "";
                if (!sheetIsNull) {
                    try {
                        Export.Sheet s = sheets[i];
                        sheetName = s.sheetName();
                        // 2. 优先使用手动设置的head
                        if (Object.class != s.head() && head != s.head()) {
                            head = s.head();
                        }
                    } catch (Exception ignored) {
                        // 避免下标越界 Null指针
                        sheetIsNull = true;
                    }
                }

                if (isNoneTemplate) {
                    ExcelUtil.doSimpleWrite(writer, i, this.getSheetName(sheetName, i + 1), head, sheetData);
                } else {
                    ExcelUtil.doSimpleFill(writer, i, this.getSheetName(sheetName, i + 1), head, sheetData);
                }
            }
        } finally {
            ExcelUtil.finish(writer);
        }
    }

    protected void setResponseHeader(HttpServletResponse resp, String fullFileName) {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        resp.setContentType("application/vnd.ms-excel");
        resp.setCharacterEncoding("UTF-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        resp.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fullFileName);
    }

    protected String getFileName(String fileName) {
        fileName = StringUtil.isBlank(fileName) ? LocalDateTime.now().format(DateConsts.YYYYMMDDHHMMSS_FORMATTER) : fileName;
        return URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");
    }

    protected String getSheetName(String sheetName, int sheetNo) {
        return StringUtil.isBlank(sheetName) ? ExcelConsts.SHEET + sheetNo : sheetName;
    }

}
