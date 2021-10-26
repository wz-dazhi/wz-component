package com.wz.webmvc.filter;

import com.wz.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @className: FilterHelper
 * @description:
 * @author: zhi
 * @date: 2020/12/16 下午2:03
 * @version: 1.0
 */
@Slf4j
public final class FilterHelper {

    public static void writeResponse(ServletResponse response, Object resObj) {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try (PrintWriter w = response.getWriter()) {
            w.write(JsonUtil.toJson(resObj));
            w.flush();
        } catch (IOException e) {
            log.error("响应异常. e: ", e);
        }
    }

}
