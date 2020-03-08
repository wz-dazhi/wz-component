package com.wz.qiniu.service;

import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.StringMap;
import com.wz.common.model.Result;

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

    Result<DefaultPutRet> upload(File file, String key);

    Result<DefaultPutRet> upload(InputStream is, String key);

    Result<DefaultPutRet> upload(InputStream is, String key, StringMap params, String mime);
}
