package com.wz.swagger;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.service.Response;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author wangzhi
 */
@Configuration
@Import(SwaggerConfiguration.class)
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration {

    @Bean("defaultApi")
    public Docket defaultApi(SwaggerProperties swaggerProperties) {
        if (Objects.isNull(swaggerProperties) || !swaggerProperties.getEnabled()) {
            return new Docket(DocumentationType.OAS_30).enable(false);
        }
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .contact(new Contact(swaggerProperties.getContact().getName(),
                        swaggerProperties.getContact().getUrl(),
                        swaggerProperties.getContact().getEmail()))
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .build();

        if (swaggerProperties.getBasePath().isEmpty()) {
            swaggerProperties.getBasePath().add("/**");
        }
        List<Predicate<String>> basePath = new ArrayList<>();
        for (String path : swaggerProperties.getBasePath()) {
            basePath.add(input -> new AntPathMatcher().match(path, input));
        }

        // exclude-path处理
        List<Predicate<String>> excludePath = new ArrayList<>();
        excludePath.add(input -> new AntPathMatcher().match("/error", input));
        for (String path : swaggerProperties.getExcludePath()) {
            excludePath.add(input -> new AntPathMatcher().match(path, input));
        }

        Docket docketForBuilder = new Docket(DocumentationType.OAS_30)
                .groupName(swaggerProperties.getGroupName())
                .enable(swaggerProperties.getEnabled())
                .host(swaggerProperties.getHost())
                .apiInfo(apiInfo)
                .securityContexts(Collections.singletonList(securityContext(swaggerProperties)))
                .globalRequestParameters(buildGlobalRequestParameters(swaggerProperties));

        if ("BasicAuth".equalsIgnoreCase(swaggerProperties.getAuthorization().getType())) {
            docketForBuilder.securitySchemes(Collections.singletonList(basicAuth(swaggerProperties)));
        } else if (!"None".equalsIgnoreCase(swaggerProperties.getAuthorization().getType())) {
            docketForBuilder.securitySchemes(Collections.singletonList(apiKey(swaggerProperties)));
        }

        // 默认全局响应消息
        defaultGlobalResponseMessage(swaggerProperties.getUseDefaultResponseMessages(), docketForBuilder);
        // 自定义全局响应消息
        buildGlobalResponseMessage(swaggerProperties, docketForBuilder);

        Docket docket = docketForBuilder.select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(
                        Predicates.and(
                                Predicates.not(Predicates.or(excludePath)),
                                Predicates.or(basePath)
                        )
                ).build();


        /* ignoredParameterTypes **/
        Class<?>[] array = new Class[swaggerProperties.getIgnoredParameterTypes().size()];
        Class<?>[] ignoredParameterTypes = swaggerProperties.getIgnoredParameterTypes().toArray(array);
        docket.ignoredParameterTypes(ignoredParameterTypes);

        return docket;
    }

    /**
     * 配置基于 ApiKey 的鉴权对象
     *
     * @return
     */
    private ApiKey apiKey(SwaggerProperties swaggerProperties) {
        return new ApiKey(swaggerProperties.getAuthorization().getName(), swaggerProperties.getAuthorization().getKeyName(), ApiKeyVehicle.HEADER.getValue());
    }

    /**
     * 配置基于 BasicAuth 的鉴权对象
     *
     * @return
     */
    private BasicAuth basicAuth(SwaggerProperties swaggerProperties) {
        return new BasicAuth(swaggerProperties.getAuthorization().getName());
    }

    /**
     * securityReferences 为配置启用的鉴权策略
     *
     * @return
     */
    private SecurityContext securityContext(SwaggerProperties swaggerProperties) {
        return SecurityContext.builder().securityReferences(defaultAuth(swaggerProperties)).build();
    }

    /**
     * 配置默认的全局鉴权策略；其中返回的 SecurityReference 中，reference 即为ApiKey对象里面的name，保持一致才能开启全局鉴权
     *
     * @return
     */
    private List<SecurityReference> defaultAuth(SwaggerProperties swaggerProperties) {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(SecurityReference.builder()
                .reference(swaggerProperties.getAuthorization().getName())
                .scopes(authorizationScopes).build());
    }

    private List<RequestParameter> buildGlobalRequestParameters(SwaggerProperties swaggerProperties) {
        List<SwaggerProperties.GlobalRequestParameter> requestParameters = swaggerProperties.getGlobalRequestParameters();
        List<RequestParameter> parameters = newArrayList();

        if (Objects.isNull(requestParameters)) {
            return parameters;
        }

        requestParameters.forEach(globalRequestParameter -> parameters.add(new RequestParameterBuilder()
                .name(globalRequestParameter.getName())
                .description(globalRequestParameter.getDescription())
                .in(globalRequestParameter.getParameterType())
                .required(Boolean.parseBoolean(globalRequestParameter.getRequired()))
                .build()));

        return parameters;
    }

    /**
     * 设置全局响应消息
     *
     * @param swaggerProperties swaggerProperties 支持 POST,GET,PUT,PATCH,DELETE,HEAD,OPTIONS,TRACE
     * @param docket            swagger docket builder
     */
    public static void buildGlobalResponseMessage(SwaggerProperties swaggerProperties, Docket docket) {
        SwaggerProperties.GlobalResponseMessage globalResponseMessages = swaggerProperties.getGlobalResponseMessage();
        if (Objects.isNull(globalResponseMessages)) {
            return;
        }
        /* POST,GET,PUT,PATCH,DELETE,HEAD,OPTIONS,TRACE 响应消息体 **/
        List<Response> postResponseMessages = getResponseList(globalResponseMessages.getPost());
        List<Response> getResponseMessages = getResponseList(globalResponseMessages.getGet());
        List<Response> putResponseMessages = getResponseList(globalResponseMessages.getPut());
        List<Response> patchResponseMessages = getResponseList(globalResponseMessages.getPatch());
        List<Response> deleteResponseMessages = getResponseList(globalResponseMessages.getDelete());
        List<Response> headResponseMessages = getResponseList(globalResponseMessages.getHead());
        List<Response> optionsResponseMessages = getResponseList(globalResponseMessages.getOptions());
        List<Response> trackResponseMessages = getResponseList(globalResponseMessages.getTrace());

        docket.useDefaultResponseMessages(swaggerProperties.getApplyDefaultResponseMessages())
                .globalResponses(HttpMethod.POST, postResponseMessages)
                .globalResponses(HttpMethod.GET, getResponseMessages)
                .globalResponses(HttpMethod.PUT, putResponseMessages)
                .globalResponses(HttpMethod.PATCH, patchResponseMessages)
                .globalResponses(HttpMethod.DELETE, deleteResponseMessages)
                .globalResponses(HttpMethod.HEAD, headResponseMessages)
                .globalResponses(HttpMethod.OPTIONS, optionsResponseMessages)
                .globalResponses(HttpMethod.TRACE, trackResponseMessages);
    }

    /**
     * 获取返回消息体列表
     *
     * @param globalResponseMessageBodyList 全局Code消息返回集合
     * @return
     */
    private static List<Response> getResponseList(List<SwaggerProperties.GlobalResponseMessageBody> globalResponseMessageBodyList) {
        List<Response> responseMessages = new ArrayList<>();
        for (SwaggerProperties.GlobalResponseMessageBody globalResponseMessageBody : globalResponseMessageBodyList) {
            final Response response = new ResponseBuilder()
                    .code(globalResponseMessageBody.getCode())
                    .description(globalResponseMessageBody.getMessage())
                    .build();
            responseMessages.add(response);
        }

        return responseMessages;
    }

    public static void defaultGlobalResponseMessage(boolean useDefaultResponseMessages, Docket docket) {
        if (!useDefaultResponseMessages) {
            return;
        }
        final Response ok = new ResponseBuilder().code("0").description("成功").build();
        final Response paramsError = new ResponseBuilder().code("-1").description("参数错误").build();
        final Response serverError = new ResponseBuilder().code("-2").description("服务器开小差, 请稍后再试").build();
        final Response requestError = new ResponseBuilder().code("-3").description("请求失败, 请稍后再试").build();
        List<Response> responses = new ArrayList<>();
        responses.add(ok);
        responses.add(paramsError);
        responses.add(serverError);
        responses.add(requestError);
        docket.useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.POST, responses)
                .globalResponses(HttpMethod.GET, responses)
                .globalResponses(HttpMethod.PUT, responses)
                .globalResponses(HttpMethod.PATCH, responses)
                .globalResponses(HttpMethod.DELETE, responses)
                .globalResponses(HttpMethod.HEAD, responses)
                .globalResponses(HttpMethod.OPTIONS, responses)
                .globalResponses(HttpMethod.TRACE, responses);
    }

}
