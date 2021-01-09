package com.wz.encrypt.algorithm;

/**
 * @projectName: wz
 * @package: com.wz.encrypt.algorithm
 * @className: SignAlgorithm
 * @description: 签名算法接口
 * @author: Zhi Wang
 * @date: 2019/3/5 5:20 PM
 * @version: 1.0
 **/
public interface SignAlgorithm {

    String encrypt(String content, String encryptKey) throws Exception;

    String decrypt(String encryptStr, String decryptKey) throws Exception;

}
