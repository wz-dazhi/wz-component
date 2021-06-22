package com.wz.excel.config;

import com.wz.excel.export.aspect.ExcelExportReturnValueHandler;
import com.wz.excel.export.handler.DefaultExportHandler;
import com.wz.excel.export.handler.ExportHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: wz-component
 * @package: com.wz.excel.config
 * @className: ExcelAutoConfiguration
 * @description:
 * @author: zhi
 * @date: 2021/6/21
 * @version: 1.0
 */
@Configuration
@ConditionalOnProperty(prefix = "excel.config", name = "enable", havingValue = "true", matchIfMissing = true)
public class ExcelAutoConfiguration implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    @ConditionalOnMissingBean
    @Bean
    public ExportHandler exportHandler() {
        return new DefaultExportHandler();
    }

    @ConditionalOnMissingBean
    @Bean
    public ExcelExportReturnValueHandler excelExportReturnValueHandler() {
        return new ExcelExportReturnValueHandler(exportHandler());
    }

    @Override
    public void afterPropertiesSet() {
        RequestMappingHandlerAdapter handlerAdapter = applicationContext.getBean(RequestMappingHandlerAdapter.class);
        List<HandlerMethodReturnValueHandler> returnValueHandlers = handlerAdapter.getReturnValueHandlers();
        // 调整处理顺序
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>();
        handlers.add(excelExportReturnValueHandler());
        handlers.addAll(returnValueHandlers);
        handlerAdapter.setReturnValueHandlers(handlers);
    }

}
