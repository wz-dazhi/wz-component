package com.wz.encrypt.algorithm;

import com.wz.common.util.AesEncryptUtil;

/**
 * @projectName: wz
 * @package: com.wz.encrypt.algorithm
 * @className: DefaultEncryptAlgorithm
 * @description: 默认加密算法实现 AES
 * @author: Zhi Wang
 * @date: 2019/3/5 5:21 PM
 * @version: 1.0
 **/
public class DefaultEncryptAlgorithm implements EncryptAlgorithm {

    @Override
    public String encrypt(String content, String encryptKey) throws Exception {
        return AesEncryptUtil.aesEncrypt(content, encryptKey);
    }

    @Override
    public String decrypt(String encryptStr, String decryptKey) throws Exception {
        return AesEncryptUtil.aesDecrypt(encryptStr, decryptKey);
    }
}
