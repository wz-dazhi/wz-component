package com.wz.webmvc.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @projectName: wz-component
 * @package: com.wz.webmvc.async
 * @className: AsyncService
 * @description:
 * @author: zhi
 * @date: 2022/10/24
 * @version: 1.0
 */
@Async
@Service
public class AsyncService {

    public Future<String> say(String name) {
        return AsyncResult.forValue("hello " + name + " --> " + Thread.currentThread().getName());
    }

    public Future<String> f() {
        throw new RuntimeException(Thread.currentThread().getName() + ", f()");
//        return AsyncResult.forValue("f()");
    }

    public void f2() {
        throw new RuntimeException(Thread.currentThread().getName() + ", f2()");
    }

}
