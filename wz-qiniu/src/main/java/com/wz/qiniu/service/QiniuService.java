package com.wz.qiniu.service;

import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.StringMap;

import java.io.File;
import java.io.InputStream;

/**
 * @projectName: wz-component
 * @package: com.wz.qiniu.service
 * @className: QiniuService
 * @description:
 * @author: Zhi
 * @date: 2020-03-08 15:58
 * @version: 1.0
 */
public interface QiniuService {

    /**
     * 上传文件
     *
     * @param file 文件File
     * @param key  文件名
     * @return
     */
    DefaultPutRet upload(File file, String key);

    DefaultPutRet upload(InputStream is, String key);

    DefaultPutRet upload(InputStream is, String key, StringMap params, String mime);

    /**
     * 获取上传凭证
     *
     * @param key
     * @return
     */
    String getUploadToken(String key);
}
