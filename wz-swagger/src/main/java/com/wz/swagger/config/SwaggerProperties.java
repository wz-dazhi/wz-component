package com.wz.swagger.config;

import com.wz.swagger.util.LocalIpUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangzhi
 */
@Data
@ConfigurationProperties(prefix = "api.swagger")
public class SwaggerProperties {
    public static final String KNIFE4J_ENABLE_NAME = "knife4j.enable";

    /**
     * 分组
     */
    private String groupName = "default";
    /**
     * 标题
     **/
    private String title = "";
    /**
     * 描述
     **/
    private String description = "";
    /**
     * 版本
     **/
    private String version = "";
    /**
     * 许可证
     **/
    private String license = "";
    /**
     * 许可证URL
     **/
    private String licenseUrl = "";
    /**
     * 服务条款URL
     **/
    private String termsOfServiceUrl = "";
    /**
     * 是否使用默认预定义的响应消息 ，默认 true
     **/
    private Boolean applyDefaultResponseMessages = true;
    /**
     * 是否使用框架内自定义响应消息, 默认为true
     */
    private Boolean useDefaultResponseMessages = true;
    /**
     * 忽略的参数类型
     **/
    private List<Class<?>> ignoredParameterTypes = new ArrayList<>();
    /**
     * 联系人信息
     */
    private Contact contact = new Contact();
    /**
     * swagger会解析的包路径
     **/
    private String basePackage = "";
    /**
     * swagger会解析的url规则
     **/
    private List<String> basePath = new ArrayList<>();
    /**
     * 在basePath基础上需要排除的url规则
     **/
    private List<String> excludePath = new ArrayList<>();
    /**
     * host信息
     **/
    private String host = LocalIpUtil.localIpv4Address();
    /**
     * 全局参数配置
     **/
    private List<GlobalRequestParameter> globalRequestParameters;
    /**
     * 全局响应消息
     **/
    private GlobalResponseMessage globalResponseMessage;
    /**
     * 全局统一鉴权配置
     **/
    private Authorization authorization = new Authorization();
    /**
     * 分组
     */
    private Map<String, DocketInfo> docket = new HashMap<>();

    @Data
    public static class Contact {
        /**
         * 联系人
         **/
        private String name = "";
        /**
         * 联系人url
         **/
        private String url = "";
        /**
         * 联系人email
         **/
        private String email = "";
    }

    @Data
    public static class DocketInfo {
        /**
         * 标题
         **/
        private String title = "";
        /**
         * 描述
         **/
        private String description = "";
        /**
         * 版本
         **/
        private String version = "";
        /**
         * 许可证
         **/
        private String license = "";
        /**
         * 许可证URL
         **/
        private String licenseUrl = "";
        /**
         * 服务条款URL
         **/
        private String termsOfServiceUrl = "";
        /**
         * 联系人信息
         */
        private Contact contact = new Contact();
        /**
         * swagger会解析的包路径
         **/
        private String basePackage = "";
        /**
         * swagger会解析的url规则
         **/
        private List<String> basePath = new ArrayList<>();
        /**
         * 在basePath基础上需要排除的url规则
         **/
        private List<String> excludePath = new ArrayList<>();

        private List<GlobalRequestParameter> globalRequestParameters;
        /**
         * 忽略的参数类型
         **/
        private List<Class<?>> ignoredParameterTypes = new ArrayList<>();
    }

    @Data
    public static class GlobalRequestParameter {
        /**
         * 参数名
         **/
        private String name;

        /**
         * 描述信息
         **/
        private String description;

        /**
         * 参数放在哪个地方:header,query,path,body.form
         **/
        private String parameterType;

        /**
         * 参数是否必须传
         **/
        private String required;

    }

    @Data
    public static class GlobalResponseMessage {
        /**
         * POST 响应消息体
         **/
        List<GlobalResponseMessageBody> post = new ArrayList<>();
        /**
         * GET 响应消息体
         **/
        List<GlobalResponseMessageBody> get = new ArrayList<>();
        /**
         * PUT 响应消息体
         **/
        List<GlobalResponseMessageBody> put = new ArrayList<>();
        /**
         * PATCH 响应消息体
         **/
        List<GlobalResponseMessageBody> patch = new ArrayList<>();
        /**
         * DELETE 响应消息体
         **/
        List<GlobalResponseMessageBody> delete = new ArrayList<>();
        /**
         * HEAD 响应消息体
         **/
        List<GlobalResponseMessageBody> head = new ArrayList<>();
        /**
         * OPTIONS 响应消息体
         **/
        List<GlobalResponseMessageBody> options = new ArrayList<>();
        /**
         * TRACE 响应消息体
         **/
        List<GlobalResponseMessageBody> trace = new ArrayList<>();
    }

    @Data
    public static class GlobalResponseMessageBody {
        /**
         * 响应码
         **/
        private String code;
        /**
         * 响应消息
         **/
        private String message;
    }

    /**
     * securitySchemes 支持方式之一 ApiKey
     */
    @Data
    static class Authorization {
        /**
         * 鉴权策略ID，对应 SecurityReferences ID
         */
        private String name = "Authorization";
        /**
         * 鉴权策略，可选 ApiKey | BasicAuth | None，默认ApiKey
         */
        private String type = "ApiKey";
        /**
         * 鉴权传递的Header参数
         */
        private String keyName = "TOKEN";
    }

}


