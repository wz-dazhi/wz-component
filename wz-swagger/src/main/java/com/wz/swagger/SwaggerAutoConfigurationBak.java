//package com.wz.swagger;
//
//import com.google.common.base.Predicate;
//import com.google.common.base.Predicates;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.util.AntPathMatcher;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.builders.RequestParameterBuilder;
//import springfox.documentation.builders.ResponseBuilder;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger.web.ApiKeyVehicle;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Objects;
//
//import static com.google.common.collect.Lists.newArrayList;
//
///**
// * @author wangzhi
// */
//@EnableOpenApi
//@Configuration
//@EnableConfigurationProperties(SwaggerProperties.class)
//public class SwaggerAutoConfigurationBak {
//
//    @Bean
//    public Docket defaultApi(SwaggerProperties swaggerProperties) {
//        ApiInfo apiInfo = new ApiInfoBuilder()
//                .title(swaggerProperties.getTitle())
//                .description(swaggerProperties.getDescription())
//                .version(swaggerProperties.getVersion())
//                .license(swaggerProperties.getLicense())
//                .licenseUrl(swaggerProperties.getLicenseUrl())
//                .contact(new Contact(swaggerProperties.getContact().getName(),
//                        swaggerProperties.getContact().getUrl(),
//                        swaggerProperties.getContact().getEmail()))
//                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
//                .build();
//
//        if (swaggerProperties.getBasePath().isEmpty()) {
//            swaggerProperties.getBasePath().add("/**");
//        }
//        List<Predicate<String>> basePath = new ArrayList<>();
//        for (String path : swaggerProperties.getBasePath()) {
//            basePath.add(input -> new AntPathMatcher().match(path, input));
//        }
//
//        // exclude-path处理
//        List<Predicate<String>> excludePath = new ArrayList<>();
//        for (String path : swaggerProperties.getExcludePath()) {
//            excludePath.add(input -> new AntPathMatcher().match(path, input));
//        }
//
//        Docket docketForBuilder = new Docket(DocumentationType.OAS_30)
//                .groupName("default-api")
//                .enable(swaggerProperties.getEnabled())
//                .host(swaggerProperties.getHost())
//                .apiInfo(apiInfo)
//                .securityContexts(Collections.singletonList(securityContext(swaggerProperties)))
//                .globalRequestParameters(buildGlobalRequestParameters(swaggerProperties));
//
//        if ("BasicAuth".equalsIgnoreCase(swaggerProperties.getAuthorization().getType())) {
//            docketForBuilder.securitySchemes(Collections.singletonList(basicAuth(swaggerProperties)));
//        } else if (!"None".equalsIgnoreCase(swaggerProperties.getAuthorization().getType())) {
//            docketForBuilder.securitySchemes(Collections.singletonList(apiKey(swaggerProperties)));
//        }
//
//        // 全局响应消息
//        buildGlobalResponseMessage(swaggerProperties, docketForBuilder);
//
//        Docket docket = docketForBuilder.select()
//                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
//                .paths(
//                        Predicates.and(
//                                Predicates.not(Predicates.or(excludePath)),
//                                Predicates.or(basePath)
//                        )
//                ).build();
//
//
//        /* ignoredParameterTypes **/
//        Class<?>[] array = new Class[swaggerProperties.getIgnoredParameterTypes().size()];
//        Class<?>[] ignoredParameterTypes = swaggerProperties.getIgnoredParameterTypes().toArray(array);
//        docket.ignoredParameterTypes(ignoredParameterTypes);
//
//        return docket;
//    }
//
////    @Bean
////    public List<Docket> createRestApi(SwaggerProperties swaggerProperties) {
////        final Boolean enabled = swaggerProperties.getEnabled();
////
////        List<Docket> docketList = new LinkedList<>();
////
////        // 没有分组
////        if (swaggerProperties.getDocket().size() == 0) {
////            ApiInfo apiInfo = new ApiInfoBuilder()
////                    .title(swaggerProperties.getTitle())
////                    .description(swaggerProperties.getDescription())
////                    .version(swaggerProperties.getVersion())
////                    .license(swaggerProperties.getLicense())
////                    .licenseUrl(swaggerProperties.getLicenseUrl())
////                    .contact(new Contact(swaggerProperties.getContact().getName(),
////                            swaggerProperties.getContact().getUrl(),
////                            swaggerProperties.getContact().getEmail()))
////                    .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
////                    .build();
////
////            // base-path处理
////            // 当没有配置任何path的时候，解析/**
////            if (swaggerProperties.getBasePath().isEmpty()) {
////                swaggerProperties.getBasePath().add("/**");
////            }
////            List<Predicate<String>> basePath = new ArrayList<>();
////            for (String path : swaggerProperties.getBasePath()) {
////                basePath.add(input -> new AntPathMatcher().match(path, input));
////            }
////
////            // exclude-path处理
////            List<Predicate<String>> excludePath = new ArrayList<>();
////            for (String path : swaggerProperties.getExcludePath()) {
////                excludePath.add(input -> new AntPathMatcher().match(path, input));
////            }
////
////            Docket docketForBuilder = new Docket(DocumentationType.OAS_30)
////                    .host(swaggerProperties.getHost())
////                    .apiInfo(apiInfo)
////                    .securityContexts(Collections.singletonList(securityContext()))
////                    .globalRequestParameters(buildGlobalRequestParametersFromSwaggerProperties(swaggerProperties.getGlobalRequestParameters()));
////
////            if ("BasicAuth".equalsIgnoreCase(swaggerProperties.getAuthorization().getType())) {
////                docketForBuilder.securitySchemes(Collections.singletonList(basicAuth()));
////            } else if (!"None".equalsIgnoreCase(swaggerProperties.getAuthorization().getType())) {
////                docketForBuilder.securitySchemes(Collections.singletonList(apiKey()));
////            }
////
////            // 全局响应消息
////            if (!swaggerProperties.getApplyDefaultResponseMessages()) {
////                buildGlobalResponseMessage(swaggerProperties, docketForBuilder);
////            }
////
////            Docket docket = docketForBuilder.select()
////                    .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
////                    .paths(
////                            Predicates.and(
////                                    Predicates.not(Predicates.or(excludePath)),
////                                    Predicates.or(basePath)
////                            )
////                    ).build();
////
////
////            /* ignoredParameterTypes **/
////            Class<?>[] array = new Class[swaggerProperties.getIgnoredParameterTypes().size()];
////            Class<?>[] ignoredParameterTypes = swaggerProperties.getIgnoredParameterTypes().toArray(array);
////            docket.ignoredParameterTypes(ignoredParameterTypes);
////            docket.enable(enabled);
////
////            beanFactory.registerSingleton("default", docket);
////            docketList.add(docket);
////            return docketList;
////        }
////
////        // 分组创建
////        for (String groupName : swaggerProperties.getDocket().keySet()) {
////            SwaggerProperties.DocketInfo docketInfo = swaggerProperties.getDocket().get(groupName);
////
////            ApiInfo apiInfo = new ApiInfoBuilder()
////                    .title(docketInfo.getTitle().isEmpty() ? swaggerProperties.getTitle() : docketInfo.getTitle())
////                    .description(docketInfo.getDescription().isEmpty() ? swaggerProperties.getDescription() : docketInfo.getDescription())
////                    .version(docketInfo.getVersion().isEmpty() ? swaggerProperties.getVersion() : docketInfo.getVersion())
////                    .license(docketInfo.getLicense().isEmpty() ? swaggerProperties.getLicense() : docketInfo.getLicense())
////                    .licenseUrl(docketInfo.getLicenseUrl().isEmpty() ? swaggerProperties.getLicenseUrl() : docketInfo.getLicenseUrl())
////                    .contact(
////                            new Contact(
////                                    docketInfo.getContact().getName().isEmpty() ? swaggerProperties.getContact().getName() : docketInfo.getContact().getName(),
////                                    docketInfo.getContact().getUrl().isEmpty() ? swaggerProperties.getContact().getUrl() : docketInfo.getContact().getUrl(),
////                                    docketInfo.getContact().getEmail().isEmpty() ? swaggerProperties.getContact().getEmail() : docketInfo.getContact().getEmail()
////                            )
////                    )
////                    .termsOfServiceUrl(docketInfo.getTermsOfServiceUrl().isEmpty() ? swaggerProperties.getTermsOfServiceUrl() : docketInfo.getTermsOfServiceUrl())
////                    .build();
////
////            // base-path处理
////            // 当没有配置任何path的时候，解析/**
////            if (docketInfo.getBasePath().isEmpty()) {
////                docketInfo.getBasePath().add("/**");
////            }
////            List<Predicate<String>> basePath = new ArrayList<>();
////            for (String path : docketInfo.getBasePath()) {
////                basePath.add(input -> new AntPathMatcher().match(path, input));
////            }
////
////            // exclude-path处理
////            List<Predicate<String>> excludePath = new ArrayList<>();
////            for (String path : docketInfo.getExcludePath()) {
////                excludePath.add(input -> new AntPathMatcher().match(path, input));
////            }
////
////            Docket docketForBuilder = new Docket(DocumentationType.OAS_30)
////                    .host(swaggerProperties.getHost())
////                    .apiInfo(apiInfo)
////                    .securityContexts(Collections.singletonList(securityContext()))
////                    .globalRequestParameters(assemblyGlobalRequestParameters(swaggerProperties.getGlobalRequestParameters(), docketInfo.getGlobalRequestParameters()));
////
////            if ("BasicAuth".equalsIgnoreCase(swaggerProperties.getAuthorization().getType())) {
////                docketForBuilder.securitySchemes(Collections.singletonList(basicAuth()));
////            } else if (!"None".equalsIgnoreCase(swaggerProperties.getAuthorization().getType())) {
////                docketForBuilder.securitySchemes(Collections.singletonList(apiKey()));
////            }
////
////            // 全局响应消息
////            if (!swaggerProperties.getApplyDefaultResponseMessages()) {
////                buildGlobalResponseMessage(swaggerProperties, docketForBuilder);
////            }
////
////            Docket docket = docketForBuilder.groupName(groupName)
////                    .select()
////                    .apis(RequestHandlerSelectors.basePackage(docketInfo.getBasePackage()))
////                    .paths(
////                            Predicates.and(
////                                    Predicates.not(Predicates.or(excludePath)),
////                                    Predicates.or(basePath)
////                            )
////                    )
////                    .build();
////
////            /* ignoredParameterTypes **/
////            Class<?>[] array = new Class[docketInfo.getIgnoredParameterTypes().size()];
////            Class<?>[] ignoredParameterTypes = docketInfo.getIgnoredParameterTypes().toArray(array);
////            docket.ignoredParameterTypes(ignoredParameterTypes);
////            docket.enable(enabled);
////
////            beanFactory.registerSingleton(groupName, docket);
////            docketList.add(docket);
////        }
////        return docketList;
////    }
//
//    /**
//     * 配置基于 ApiKey 的鉴权对象
//     *
//     * @return
//     */
//    private ApiKey apiKey(SwaggerProperties swaggerProperties) {
//        return new ApiKey(swaggerProperties.getAuthorization().getName(), swaggerProperties.getAuthorization().getKeyName(), ApiKeyVehicle.HEADER.getValue());
//    }
//
//    /**
//     * 配置基于 BasicAuth 的鉴权对象
//     *
//     * @return
//     */
//    private BasicAuth basicAuth(SwaggerProperties swaggerProperties) {
//        return new BasicAuth(swaggerProperties.getAuthorization().getName());
//    }
//
//    /**
//     * securityReferences 为配置启用的鉴权策略
//     *
//     * @return
//     */
//    private SecurityContext securityContext(SwaggerProperties swaggerProperties) {
//        return SecurityContext.builder().securityReferences(defaultAuth(swaggerProperties)).build();
//    }
//
//    /**
//     * 配置默认的全局鉴权策略；其中返回的 SecurityReference 中，reference 即为ApiKey对象里面的name，保持一致才能开启全局鉴权
//     *
//     * @return
//     */
//    private List<SecurityReference> defaultAuth(SwaggerProperties swaggerProperties) {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Collections.singletonList(SecurityReference.builder()
//                .reference(swaggerProperties.getAuthorization().getName())
//                .scopes(authorizationScopes).build());
//    }
//
//    private List<RequestParameter> buildGlobalRequestParameters(SwaggerProperties swaggerProperties) {
//        List<SwaggerProperties.GlobalRequestParameter> requestParameters = swaggerProperties.getGlobalRequestParameters();
//        List<RequestParameter> parameters = newArrayList();
//
//        if (Objects.isNull(requestParameters)) {
//            return parameters;
//        }
//
//        requestParameters.forEach(globalRequestParameter -> parameters.add(new RequestParameterBuilder()
//                .name(globalRequestParameter.getName())
//                .description(globalRequestParameter.getDescription())
//                .in(globalRequestParameter.getParameterType())
//                .required(Boolean.parseBoolean(globalRequestParameter.getRequired()))
//                .build()));
//
//        return parameters;
//    }
//
//    /**
//     * 局部参数按照name覆盖局部参数
//     *
//     * @param globalRequestParameters
//     * @param docketOperationParameters
//     * @return
//     */
////    private List<RequestParameter> assemblyGlobalRequestParameters(List<SwaggerProperties.GlobalRequestParameter> globalRequestParameters,
////                                                                   List<SwaggerProperties.GlobalRequestParameter> docketOperationParameters) {
////        if (Objects.isNull(docketOperationParameters) || docketOperationParameters.isEmpty()) {
////            return buildGlobalRequestParameters(globalRequestParameters);
////        }
////
////        Set<String> docketNames = docketOperationParameters.stream()
////                .map(SwaggerProperties.GlobalRequestParameter::getName)
////                .collect(Collectors.toSet());
////
////        List<SwaggerProperties.GlobalRequestParameter> resultOperationParameters = newArrayList();
////
////        if (Objects.nonNull(globalRequestParameters)) {
////            for (SwaggerProperties.GlobalRequestParameter parameter : globalRequestParameters) {
////                if (!docketNames.contains(parameter.getName())) {
////                    resultOperationParameters.add(parameter);
////                }
////            }
////        }
////
////        resultOperationParameters.addAll(docketOperationParameters);
////        return buildGlobalRequestParameters(resultOperationParameters);
////    }
//
//    /**
//     * 设置全局响应消息
//     *
//     * @param swaggerProperties swaggerProperties 支持 POST,GET,PUT,PATCH,DELETE,HEAD,OPTIONS,TRACE
//     * @param docketForBuilder  swagger docket builder
//     */
//    public static void buildGlobalResponseMessage(SwaggerProperties swaggerProperties, Docket docketForBuilder) {
//        SwaggerProperties.GlobalResponseMessage globalResponseMessages = swaggerProperties.getGlobalResponseMessage();
//        /* POST,GET,PUT,PATCH,DELETE,HEAD,OPTIONS,TRACE 响应消息体 **/
//        List<Response> postResponseMessages = getResponseList(globalResponseMessages.getPost());
//        List<Response> getResponseMessages = getResponseList(globalResponseMessages.getGet());
//        List<Response> putResponseMessages = getResponseList(globalResponseMessages.getPut());
//        List<Response> patchResponseMessages = getResponseList(globalResponseMessages.getPatch());
//        List<Response> deleteResponseMessages = getResponseList(globalResponseMessages.getDelete());
//        List<Response> headResponseMessages = getResponseList(globalResponseMessages.getHead());
//        List<Response> optionsResponseMessages = getResponseList(globalResponseMessages.getOptions());
//        List<Response> trackResponseMessages = getResponseList(globalResponseMessages.getTrace());
//
//        docketForBuilder.useDefaultResponseMessages(swaggerProperties.getApplyDefaultResponseMessages())
//                .globalResponses(HttpMethod.POST, postResponseMessages)
//                .globalResponses(HttpMethod.GET, getResponseMessages)
//                .globalResponses(HttpMethod.PUT, putResponseMessages)
//                .globalResponses(HttpMethod.PATCH, patchResponseMessages)
//                .globalResponses(HttpMethod.DELETE, deleteResponseMessages)
//                .globalResponses(HttpMethod.HEAD, headResponseMessages)
//                .globalResponses(HttpMethod.OPTIONS, optionsResponseMessages)
//                .globalResponses(HttpMethod.TRACE, trackResponseMessages);
//    }
//
//    /**
//     * 获取返回消息体列表
//     *
//     * @param globalResponseMessageBodyList 全局Code消息返回集合
//     * @return
//     */
//    private static List<Response> getResponseList(List<SwaggerProperties.GlobalResponseMessageBody> globalResponseMessageBodyList) {
//        List<Response> responseMessages = new ArrayList<>();
//        for (SwaggerProperties.GlobalResponseMessageBody globalResponseMessageBody : globalResponseMessageBodyList) {
//            final Response response = new ResponseBuilder().code(globalResponseMessageBody.getCode())
//                    .description(globalResponseMessageBody.getMessage())
//                    .build();
//            responseMessages.add(response);
//        }
//
//        return responseMessages;
//    }
//}
