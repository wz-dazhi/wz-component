package com.wz.excel.write.aspect;

import com.wz.excel.annotation.Export;
import com.wz.excel.write.handler.ExportHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author zhi
 */
@RequiredArgsConstructor
public class ExcelExportReturnValueHandler implements HandlerMethodReturnValueHandler {
    private final ExportHandler exportHandler;

    @Override
    public boolean supportsReturnType(MethodParameter parameter) {
        return parameter.hasMethodAnnotation(Export.class);
    }

    @Override
    public void handleReturnValue(Object returnData,
                                  MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest nativeWebRequest) {
        Export export = parameter.getMethodAnnotation(Export.class);
        exportHandler.export(returnData, parameter, export);
    }

}
