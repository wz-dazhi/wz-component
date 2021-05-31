package com.wz.webmvc.wrapper;

import lombok.Cleanup;
import lombok.Getter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @projectName: wz-component
 * @package: com.wz.webmvc.wrapper
 * @className: RequestBodyWrapper
 * @description:
 * @author: zhi
 * @date: 2021/1/9 下午4:10
 * @version: 1.0
 */
public class RequestBodyWrapper extends HttpServletRequestWrapper {

    @Getter
    private String body;

    public RequestBodyWrapper(HttpServletRequest req) throws IOException {
        super(req);
        req.setCharacterEncoding("UTF-8");
        @Cleanup final BufferedReader reader = req.getReader();
        body = reader.lines().reduce(String::concat).orElse(null);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new RequestBodyServletInputStream(body);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    private static class RequestBodyServletInputStream extends ServletInputStream {
        private final ByteArrayInputStream byteArrayInputStream;

        public RequestBodyServletInputStream(String body) {
            byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read() throws IOException {
            return byteArrayInputStream.read();
        }

    }

}
