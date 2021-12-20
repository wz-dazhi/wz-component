//package com.wz.webmvc.config;
//
//import lombok.AllArgsConstructor;
//import org.apache.catalina.startup.Tomcat;
//import org.apache.coyote.UpgradeProtocol;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
//import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
//import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.Servlet;
//import java.util.stream.Collectors;
//
///**
// * @projectName: pot-circle
// * @package: com.wz.potcircle.config
// * @className: TomcatConfig
// * @description: 弃用当前类, 如果URL中存在特殊字符, 建议使用post请求, 将特殊字符放到body中
// * @author: Zhi
// * @date: 2020-01-11 21:58
// * @version: 1.0
// */
//@Configuration
//@ConditionalOnClass({Servlet.class, Tomcat.class, UpgradeProtocol.class})
//public class TomcatConfig {
//
//    @Bean
//    public ServletWebServerFactory servletWebServerFactory(ObjectProvider<TomcatConnectorCustomizer> connectorCustomizers,
//                                                           ObjectProvider<TomcatContextCustomizer> contextCustomizers,
//                                                           ObjectProvider<TomcatProtocolHandlerCustomizer<?>> protocolHandlerCustomizers) {
//        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//        factory.addConnectorCustomizers(connector -> {
//            connector.setProperty("relaxedPathChars", "\"<>[\\]^`{|}");
//            connector.setProperty("relaxedQueryChars", "\"<>[\\]^`{|}");
//        });
//        factory.getTomcatConnectorCustomizers().addAll(connectorCustomizers.orderedStream().collect(Collectors.toList()));
//        factory.getTomcatContextCustomizers().addAll(contextCustomizers.orderedStream().collect(Collectors.toList()));
//        factory.getTomcatProtocolHandlerCustomizers().addAll(protocolHandlerCustomizers.orderedStream().collect(Collectors.toList()));
//        return factory;
//    }
//
//}
