package com.wz.common.util;

import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

/**
 * @projectName: wz-component
 * @package: com.wz.common.util
 * @className: RestUtil
 * @description:
 * @author: Zhi
 * @date: 2020-02-18 14:28
 * @version: 1.0
 */
@Getter
@AllArgsConstructor
public class RestUtil {

    private final RestTemplate restTemplate;

    public RestUtil() {
        this.restTemplate = RestSingleton.getTemplate();
    }

    public <R> R get(String url, Class<R> resType) {
        return this.exchange(url, HttpMethod.GET, null, resType).getBody();
    }

    public <P, R> R get(String url, P params, Class<R> resType) {
        return this.exchange(this.urlParams(url, params), HttpMethod.GET, null, resType).getBody();
    }

    public <P, R> R get(String url, P params, HttpHeaders headers, Class<R> resType) {
        return this.exchange(this.urlParams(url, params), HttpMethod.GET, new HttpEntity<>(headers), resType).getBody();
    }

    public <R> R get(String url, Map<String, Object> params, HttpHeaders headers, Class<R> resType) {
        return this.exchange(this.urlParams(url, params), HttpMethod.GET, new HttpEntity<>(headers), resType).getBody();
    }

    /**
     * @param url          http://localhost:8080/user/{id}
     * @param resType      User.class
     * @param uriVariables 1
     * @return R
     */
    public <R> R getVariables(String url, Class<R> resType, Object... uriVariables) {
        return this.getVariables(url, null, resType, uriVariables);
    }

    public <R> R getVariables(String url, HttpHeaders headers, Class<R> resType, Object... uriVariables) {
        return this.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), resType, uriVariables).getBody();
    }

    public <T> ResponseEntity<T> getEntity(String url, HttpHeaders headers, Class<T> resType, Object... uriVariables) {
        return this.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), resType, uriVariables);
    }

    public <T, B> T post(String url, B body, Class<T> resType) {
        return this.exchange(url, HttpMethod.POST, new HttpEntity<>(body), resType).getBody();
    }

    public <T, B> T post(String url, HttpHeaders headers, B body, Class<T> resType) {
        return this.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), resType).getBody();
    }

    public <T, B> ResponseEntity<T> postEntity(String url, HttpHeaders headers, B body, Class<T> resType) {
        return this.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), resType);
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> httpEntity, Class<T> responseType, Object... uriVariables) {
        return restTemplate.exchange(url, method, httpEntity, responseType, uriVariables);
    }

    public <P> String urlParams(String url, P p) {
        if (null == p) {
            return url;
        }
        return urlParams(url, MapUtil.beanToMap(p));
    }

    public String urlParams(String url, Map<String, Object> params) {
        if (null != params && !params.isEmpty()) {
            StringBuilder sb = new StringBuilder("?");
            params.forEach((k, v) -> {
                if (null != v) {
                    sb.append(k).append("=").append(v).append("&");
                }
            });
            String s = sb.toString();
            if (s.endsWith("&")) {
                s = s.substring(0, s.length() - 1);
            }
            url += s;
        }
        return url;
    }

    private static class RestSingleton {
        private static final RestTemplate TEMPLATE;

        static {
            TEMPLATE = new RestTemplate();
            MappingJackson2HttpMessageConverter jackson = new MappingJackson2HttpMessageConverter();
            jackson.setObjectMapper(JsonUtil.getMapper());
            jackson.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML));
            TEMPLATE.getMessageConverters().add(jackson);
        }

        static RestTemplate getTemplate() {
            return TEMPLATE;
        }
    }

    public static void main(String[] args) {
        RestUtil r = new RestUtil();

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:72.0) Gecko/20100101 Firefox/72.0");
        ResponseEntity<JSONObject> entity = r.getEntity("http://www.tianqiapi.com/api?version=v9&appid=23035354&appsecret=8YvlPNrz", headers, JSONObject.class);
        System.out.println(entity.getStatusCode());
        System.out.println(entity.getHeaders());
        System.out.println(entity.getBody());

        System.out.println("----------");
        JSONObject map = r.get("http://www.tianqiapi.com/api?version=v9&appid=23035354&appsecret=8YvlPNrz", null, headers, JSONObject.class);
        System.out.println(map);
    }

}
