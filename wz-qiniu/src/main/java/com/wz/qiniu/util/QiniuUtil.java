package com.wz.qiniu.util;

import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.StringMap;
import com.wz.common.util.SpringContextUtil;
import com.wz.qiniu.service.QiniuService;

import java.io.File;
import java.io.InputStream;

/**
 * @projectName: wz-component
 * @package: com.wz.qiniu.util
 * @className: QiniuUtil
 * @description:
 * @author: zhi
 * @date: 2021/5/14
 * @version: 1.0
 */
public final class QiniuUtil {
    private static QiniuService qiniuService;

    private QiniuUtil() {
    }

    static {
        qiniuService = SpringContextUtil.getBean(QiniuService.class);
    }

    public static DefaultPutRet upload(File file, String key) {
        return qiniuService.upload(file, key);
    }

    public static DefaultPutRet upload(InputStream is, String key) {
        return qiniuService.upload(is, key);
    }

    public static DefaultPutRet upload(InputStream is, String key, StringMap params, String mime) {
        return qiniuService.upload(is, key, params, mime);
    }

    /**
     * 获取上传凭证
     */
    public static String getUploadToken(String key) {
        return qiniuService.getUploadToken(key);
    }
}
