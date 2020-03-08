//package com.wz.web.config;
//
//import org.springframework.boot.web.server.ConfigurableWebServerFactory;
//import org.springframework.boot.web.server.ErrorPage;
//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//
//import java.util.LinkedHashSet;
//import java.util.Set;
//
///**
// * @projectName: wz-component
// * @package: com.wz.web.config
// * @className: ErrorPageConfig
// * @description:
// * @author: Zhi
// * @date: 2019-11-06 15:26
// * @version: 1.0
// */
//@Configuration
//public class ErrorPageConfig {
//    @Bean
//    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactory() {
//        return (factory -> factory.setErrorPages(this.getErrorPage()));
//    }
//
//    private Set<ErrorPage> getErrorPage() {
//        Set<ErrorPage> pages = new LinkedHashSet<>(3);
//        pages.add(new ErrorPage(HttpStatus.UNAUTHORIZED, "/static/error/401.html"));
//        pages.add(new ErrorPage(HttpStatus.NOT_FOUND, "/static/error/404.html"));
//        pages.add(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/static/error/500.html"));
//        return pages;
//    }
//}
