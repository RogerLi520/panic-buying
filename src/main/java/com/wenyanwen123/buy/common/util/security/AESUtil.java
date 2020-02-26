package com.wenyanwen123.buy.common.util.security;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @Desc AES加密
 * @Author liww
 * @Date 2019/6/10
 * @Version 1.0
 */
public class AESUtil {

    private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding"; // 默认的加密算法

    /**
     * AES 加密操作
     * @param content
     * @return
     */
    public static String encrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM); // 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(KEY_ALGORITHM)); // 初始化为加密模式的密码器
            byte[] result = cipher.doFinal(byteContent); // 加密
            return Base64.encodeBase64String(result); // 通过Base64转码返回
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
        return null;
    }

    /**
     * AES 解密操作
     * @param content
     * @param
     * @return
     */
    public static String decrypt(String content) {
        try {
            // 实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            // 使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(KEY_ALGORITHM));
            // 执行操作
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            return new String(result, "utf-8");
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
        return null;
    }

    /**
     * 生成加密秘钥
     * @return
     */
    private static SecretKeySpec getSecretKey(final String key) throws NoSuchAlgorithmException {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes());
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            //AES 要求密钥长度为 128
            kg.init(128, random);
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex.toString());
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param data           //密文，被加密的数据
     * @param key            //秘钥
     * @param iv             //偏移量
     * @param encodingFormat //解密后的结果需要进行的编码
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String key, String iv, String encodingFormat)
            throws Exception {

        // 被加密的数据
        byte[] dataByte = Base64.decodeBase64(data);
        // 加密秘钥
        byte[] keyByte = Base64.decodeBase64(key);
        // 偏移量
        byte[] ivByte = Base64.decodeBase64(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));
        cipher.init(Cipher.DECRYPT_MODE, spec, parameters); // 初始化
        byte[] resultByte = cipher.doFinal(dataByte);
        if (null != resultByte && resultByte.length > 0) {
            String result = new String(resultByte, encodingFormat);
            return result;
        }
        return null;

        /*AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivByte);

        byte[] dataResult = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            dataResult = cipher.doFinal(dataByte);
        }catch (Exception e) {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            dataResult = cipher.doFinal(dataByte);
        }
        if (null != dataResult && dataResult.length > 0) {
            String result = new String(dataResult, encodingFormat);
            return result;
        }*/

        /*BASE64Decoder decoder = new BASE64Decoder();
        byte[] raw = decoder.decodeBuffer(key);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv2 = new IvParameterSpec(decoder.decodeBuffer(iv));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv2);
        byte[] myendicod = decoder.decodeBuffer(data);
        byte[] original = cipher.doFinal(myendicod);
        String originalString = new String(original, encodingFormat);
        return originalString;*/

    }

}
