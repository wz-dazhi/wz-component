package com.wz.swagger.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.wz.common.enums.ResultEnum;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @projectName: wz-component
 * @package: com.wz.swagger
 * @className: SwaggerBeanDefinitionRegistryPostProcessor
 * @description:
 * @author: zhi
 * @date: 2021/8/12
 * @version: 1.0
 */
public class SwaggerBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {
    private boolean swaggerEnabled;
    private SwaggerProperties swaggerProperties;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if (!swaggerEnabled) {
            return;
        }

        if (swaggerProperties != null) {
            if (swaggerProperties.getDocket().isEmpty()) {
                doRegistrySingleDocket(registry);
            } else {
                doRegistryMultiDocket(registry, swaggerProperties.getDocket());
            }
        }
    }

    private void doRegistryMultiDocket(BeanDefinitionRegistry registry, Map<String, SwaggerProperties.DocketInfo> docketInfoMap) {
        final String host = swaggerProperties.getHost();
        docketInfoMap.forEach((groupName, d) -> {
            final SwaggerProperties.Contact c = d.getContact();
            ApiInfo apiInfo = new ApiInfoBuilder()
                    .title(d.getTitle())
                    .description(d.getDescription())
                    .version(d.getVersion())
                    .license(d.getLicense())
                    .licenseUrl(d.getLicenseUrl())
                    .contact(new Contact(c.getName(), c.getUrl(), c.getEmail()))
                    .termsOfServiceUrl(d.getTermsOfServiceUrl())
                    .build();

            List<Predicate<String>> basePath = basePaths(d.getBasePath());
            List<Predicate<String>> excludePath = excludePaths(d.getExcludePath());
            Docket docketForBuilder = new Docket(DocumentationType.OAS_30)
                    .groupName(groupName)
                    .host(host)
                    .apiInfo(apiInfo)
                    .securityContexts(Collections.singletonList(securityContext(swaggerProperties)))
                    .globalRequestParameters(buildGlobalRequestParameters(d.getGlobalRequestParameters()));
            globalSettings(docketForBuilder);
            Docket docket = docketForBuilder.select()
                    .apis(RequestHandlerSelectors.basePackage(d.getBasePackage()))
                    .paths(
                            Predicates.and(
                                    Predicates.not(Predicates.or(excludePath)),
                                    Predicates.or(basePath)
                            )
                    ).build();

            final List<Class<?>> ignoredParameterTypeList = d.getIgnoredParameterTypes();
            Class<?>[] array = new Class[ignoredParameterTypeList.size()];
            Class<?>[] ignoredParameterTypes = ignoredParameterTypeList.toArray(array);
            docket.ignoredParameterTypes(ignoredParameterTypes);
            final RootBeanDefinition beanDefinition = new RootBeanDefinition(Docket.class, () -> docket);
            registry.registerBeanDefinition(groupName, beanDefinition);
        });
    }

    private void doRegistrySingleDocket(BeanDefinitionRegistry registry) {
        final SwaggerProperties.Contact c = swaggerProperties.getContact();
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .contact(new Contact(c.getName(), c.getUrl(), c.getEmail()))
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .build();

        List<Predicate<String>> basePath = basePaths(swaggerProperties.getBasePath());
        List<Predicate<String>> excludePath = excludePaths(swaggerProperties.getExcludePath());
        Docket docketForBuilder = new Docket(DocumentationType.OAS_30)
                .groupName(swaggerProperties.getGroupName())
                .host(swaggerProperties.getHost())
                .apiInfo(apiInfo)
                .securityContexts(Collections.singletonList(securityContext(swaggerProperties)))
                .globalRequestParameters(buildGlobalRequestParameters(swaggerProperties.getGlobalRequestParameters()));
        globalSettings(docketForBuilder);

        Docket docket = docketForBuilder.select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(
                        Predicates.and(
                                Predicates.not(Predicates.or(excludePath)),
                                Predicates.or(basePath)
                        )
                ).build();

        Class<?>[] array = new Class[swaggerProperties.getIgnoredParameterTypes().size()];
        Class<?>[] ignoredParameterTypes = swaggerProperties.getIgnoredParameterTypes().toArray(array);
        docket.ignoredParameterTypes(ignoredParameterTypes);
        final RootBeanDefinition beanDefinition = new RootBeanDefinition(Docket.class, () -> docket);
        registry.registerBeanDefinition(swaggerProperties.getGroupName(), beanDefinition);
    }

    private void globalSettings(Docket docketForBuilder) {
        if ("BasicAuth".equalsIgnoreCase(swaggerProperties.getAuthorization().getType())) {
            docketForBuilder.securitySchemes(Collections.singletonList(basicAuth(swaggerProperties)));
        } else if (!"None".equalsIgnoreCase(swaggerProperties.getAuthorization().getType())) {
            docketForBuilder.securitySchemes(Collections.singletonList(apiKey(swaggerProperties)));
        }
        // 默认全局响应消息
        defaultGlobalResponseMessage(swaggerProperties.getUseDefaultResponseMessages(), docketForBuilder);
        // 自定义全局响应消息
        buildGlobalResponseMessage(swaggerProperties, docketForBuilder);
    }

    private List<Predicate<String>> basePaths(List<String> basePaths) {
        if (basePaths.isEmpty()) {
            basePaths.add("/**");
        }
        return basePaths.stream()
                .map(path -> (Predicate<String>) input -> new AntPathMatcher().match(path, input))
                .collect(Collectors.toList());
    }

    private List<Predicate<String>> excludePaths(List<String> excludePaths) {
        final List<Predicate<String>> excludePath = excludePaths.stream()
                .map(path -> (Predicate<String>) input -> new AntPathMatcher().match(path, input))
                .collect(Collectors.toList());
        excludePath.add(0, input -> new AntPathMatcher().match("/error", input));
        return excludePath;
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

    private List<RequestParameter> buildGlobalRequestParameters(List<SwaggerProperties.GlobalRequestParameter> requestParameters) {
        List<RequestParameter> parameters = new ArrayList<>();
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
        List<Response> responses = new ArrayList<>();
        responses.add(response(ResultEnum.OK));
        responses.add(response(ResultEnum.PARAM_ERROR));
        responses.add(response(ResultEnum.SYSTEM_ERROR));
        responses.add(response(ResultEnum.REQUEST_ERROR));
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

    private static Response response(ResultEnum r) {
        return new ResponseBuilder()
                .code(r.code())
                .description(r.desc())
                .build();
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    @Override
    public void setEnvironment(Environment environment) {
        final String swaggerEnable = environment.getProperty("knife4j.enable");
        this.swaggerEnabled = StringUtils.hasLength(swaggerEnable) && Boolean.parseBoolean(swaggerEnable);
        final ConfigurationProperties p = SwaggerProperties.class.getAnnotation(ConfigurationProperties.class);
        if (null != p) {
            final String prefix = p.prefix();
            final Binder binder = Binder.get(environment);
            this.swaggerProperties = binder.bind(prefix, SwaggerProperties.class).orElse(new SwaggerProperties());
            this.swaggerProperties.setDocket(binder.bind(prefix + ".docket", Bindable.mapOf(String.class, SwaggerProperties.DocketInfo.class)).orElse(new HashMap<>()));
        }
    }

}
