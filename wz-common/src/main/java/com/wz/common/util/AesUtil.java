package com.wz.common.util;

import com.wz.common.exception.ExceptionUtil;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @projectName: wz
 * @package: com.wz.common.util
 * @className: AesEncryptUtil
 * @description: AES 工具类
 * @author: Zhi Wang
 * @date: 2019/3/5 5:26 PM
 * @version: 1.0
 **/
public final class AesUtil {

    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    private AesUtil() {
    }

    public static String base64Encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    public static byte[] base64Decode(String base64Code) {
        return Base64.decodeBase64(base64Code);
    }

    public static byte[] aesEncryptToBytes(String content, String encryptKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
            return cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw ExceptionUtil.wrap(e);
        }
    }

    public static String aesEncrypt(String content, String encryptKey) {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
            return new String(decryptBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw ExceptionUtil.wrap(e);
        }
    }

    public static String aesDecrypt(String encryptStr, String decryptKey) {
        return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    public static void main(String[] args) throws Exception {
        String key = "1234567890abcdef";
        String content = "{\"sid\":1,\"course\":\"js\",\"score\":9,\"signTime\":1552040145177}";
        System.out.println("加密前：" + content);

        String encrypt = aesEncrypt(content, key);
        System.out.println(encrypt.length() + ":加密后：" + encrypt);

        encrypt = "hxJo9ApWtPPDdkSkCHMj2lZQMvs7C1GPfT76WYuuOp5BEDclP0MzYFu9EwuqAdPNG1BBxFDPZ14V1+N5R/38Vg==";
        String decrypt = aesDecrypt(encrypt, key);
        System.out.println("解密后：" + decrypt);
    }

}
