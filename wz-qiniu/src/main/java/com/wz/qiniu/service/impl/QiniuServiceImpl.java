package com.wz.qiniu.service.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.wz.common.constant.DateConsts;
import com.wz.common.model.Result;
import com.wz.common.util.DateUtil;
import com.wz.common.util.JsonUtil;
import com.wz.common.util.Results;
import com.wz.common.util.StringUtil;
import com.wz.qiniu.config.QiniuProperties;
import com.wz.qiniu.enums.QiniuEnum;
import com.wz.qiniu.service.QiniuService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

/**
 * @projectName: wz-component
 * @package: com.wz.qiniu.service.impl
 * @className: QiniuServiceImpl
 * @description:
 * @author: Zhi
 * @date: 2020-03-08 15:59
 * @version: 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class QiniuServiceImpl implements QiniuService {
    private final QiniuProperties qiNiuProperties;
    private final Auth auth;
    private final UploadManager uploadManager;

    @Override
    public Result<DefaultPutRet> upload(File file, String key) {
        key = this.getKey(file.getName(), key);
        try {
            Response res = uploadManager.put(file, key, getToken(key));
            return Results.ok(JsonUtil.toBean(res.bodyString(), DefaultPutRet.class));
        } catch (QiniuException e) {
            log.error("上传文件失败. msg: {}, e: ", e.error(), e);
            return Results.fail(QiniuEnum.UPLOAD_FAIL);
        }
    }

    @Override
    public Result<DefaultPutRet> upload(InputStream is, String key) {
        return this.upload(is, key, null, null);
    }

    @Override
    public Result<DefaultPutRet> upload(InputStream is, String key, StringMap params, String mime) {
        key = this.getKey(null, key);
        try {
            Response res = uploadManager.put(is, key, getToken(key), params, mime);
            return Results.ok(JsonUtil.toBean(res.bodyString(), DefaultPutRet.class));
        } catch (QiniuException e) {
            log.error("上传文件失败. msg: {}, e: ", e.error(), e);
            return Results.fail(QiniuEnum.UPLOAD_FAIL);
        }
    }

    private String getToken(String key) {
        return auth.uploadToken(qiNiuProperties.getBucket(), key);
    }

    private String getKey(String fileName, String key) {
        if (StringUtil.isBlank(key)) {
            if (StringUtil.isBlank(fileName)) {
                return DateUtil.getCurrentDateTimeStr(DateConsts.YYYYMMDDHHMMSS_FORMATTER);
            }
            return DateUtil.getCurrentDateTimeStr(DateConsts.YYYYMMDDHHMMSS_FORMATTER) + fileName.substring(fileName.lastIndexOf('.'));
        }
        return key;
    }
}
