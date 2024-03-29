package com.wz.common.util;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @projectName: wz-common
 * @package: com.wz.common.util
 * @className: FutureUtil
 * @description:
 * @author: Zhi
 * @date: 2019-11-21 18:35
 * @version: 1.0
 */
public final class FutureUtil {

    private FutureUtil() {
    }

    public static <T> void cancelList(List<Future<T>> futures) {
        futures.parallelStream().forEach(FutureUtil::cancel);
    }

    public static <T> void cancel(Future<T> f) {
        if (f.isDone()) {
            f.cancel(true);
        }
    }

}
