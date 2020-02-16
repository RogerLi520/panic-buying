package com.wenyanwen123.buy.commons.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 加密工具类
 * @Author liww
 * @Date 2019/3/28
 * @Version 1.0
 */
public class EncryptionUtil {

    public static final Logger logger = LoggerFactory.getLogger(EncryptionUtil.class);

    /**
     * @Desc MD5加密
     * @Author liww
     * @Date 2019/3/28
     * @Param [password, account]
     * @return java.lang.String
     */
    public static String md5WithSalt(String password, String account) {
        logger.debug("MD5加密，参数，password：{}，account：{}", password, account);
        String hashAlgorithmName = "MD5"; // 加密方式
        Object credentials = password; // 要加密的对象
        ByteSource salt = ByteSource.Util.bytes(account); // 以账号作为盐值
        int hashIterations = 2; // 加密次数
        SimpleHash hash = new SimpleHash(hashAlgorithmName , credentials, salt, hashIterations);
        logger.debug("MD5加密，结果，result：{}", hash.toString());
        return hash.toString();
    }

}
