package com.wz.encrypt.algorithm;

import com.wz.common.util.AesUtil;

/**
 * @projectName: wz-component
 * @package: com.wz.encrypt.algorithm
 * @className: DefaultSignAlgorithm
 * @description:
 * @author: zhi
 * @date: 2020/12/30 下午3:30
 * @version: 1.0
 */
public class DefaultSignAlgorithm implements SignAlgorithm {

    @Override
    public String encrypt(String content, String encryptKey) throws Exception {
        return AesUtil.aesEncrypt(content, encryptKey);
    }

    @Override
    public String decrypt(String encryptStr, String decryptKey) throws Exception {
        return AesUtil.aesDecrypt(encryptStr, decryptKey);
    }

}
