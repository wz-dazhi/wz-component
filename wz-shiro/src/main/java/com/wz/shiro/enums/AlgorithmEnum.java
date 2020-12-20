package com.wz.shiro.enums;

import org.apache.shiro.crypto.hash.*;

/**
 * @projectName: wz-shiro
 * @package: com.wz.shiro.enums
 * @className: AlgorithmEnum
 * @description:
 * @author: Zhi Wang
 * @createDate: 2020/12/15 上午10:39
 **/
public enum AlgorithmEnum {
    /**
     * MD2
     */
    MD2(Md2Hash.ALGORITHM_NAME),

    /**
     * MD5
     */
    MD5(Md5Hash.ALGORITHM_NAME),

    /**
     * SHA-1
     */
    SHA_1(Sha1Hash.ALGORITHM_NAME),

    /**
     * SHA-256
     */
    SHA_256(Sha256Hash.ALGORITHM_NAME),

    /**
     * SHA-512
     */
    SHA_512(Sha512Hash.ALGORITHM_NAME);

    private String algorithmName;

    AlgorithmEnum(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

}
