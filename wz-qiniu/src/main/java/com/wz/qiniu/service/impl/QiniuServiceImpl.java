package com.wz.qiniu.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.wz.common.constant.DateConsts;
import com.wz.common.util.DateUtil;
import com.wz.common.util.JsonUtil;
import com.wz.common.util.StringUtil;
import com.wz.qiniu.config.QiniuProperties;
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
    public DefaultPutRet upload(File file, String key) {
        key = this.createFileKey(file.getName(), key);
        try {
            Response res = uploadManager.put(file, key, getUploadToken(key));
            return JsonUtil.toBean(res.bodyString(), DefaultPutRet.class);
        } catch (QiniuException e) {
            log.error("上传文件失败. e: ", e);
            throw new com.wz.qiniu.exception.QiniuException();
        }
    }

    @Override
    public DefaultPutRet upload(InputStream is, String key) {
        return this.upload(is, key, null, null);
    }

    @Override
    public DefaultPutRet upload(InputStream is, String key, StringMap params, String mime) {
        key = this.createFileKey(null, key);
        try {
            Response res = uploadManager.put(is, key, getUploadToken(key), params, mime);
            return JsonUtil.toBean(res.bodyString(), DefaultPutRet.class);
        } catch (QiniuException e) {
            log.error("上传文件失败. e: ", e);
            throw new com.wz.qiniu.exception.QiniuException();
        }
    }

    @Override
    public String getUploadToken(String key) {
        return auth.uploadToken(qiNiuProperties.getBucket(), key);
    }

    private String createFileKey(String fileName, String key) {
        if (StringUtil.isBlank(key)) {
            final String currentDate = DateUtil.getCurrentDateTimeStr(DateConsts.YYYY_MM_DD_HH_PATH_FORMATTER);
            if (StringUtil.isBlank(fileName)) {
                return currentDate + "/" + RandomUtil.randomString(6);
            }
            return currentDate + "/" + fileName;
        }
        return key;
    }
}
