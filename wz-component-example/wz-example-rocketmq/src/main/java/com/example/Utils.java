package com.example;

import lombok.experimental.UtilityClass;

import java.util.concurrent.TimeUnit;

/**
 * @projectName: wz-component
 * @package: com.example
 * @className: Utils
 * @description:
 * @author: zhi
 * @date: 2023/2/13
 * @version: 1.0
 */
@UtilityClass
public class Utils {

    public static void sleepSeconds(int timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
