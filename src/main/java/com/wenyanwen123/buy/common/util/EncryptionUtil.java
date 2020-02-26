package com.wenyanwen123.buy.common.util;

import org.apache.commons.codec.digest.DigestUtils;
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

    /**
     * @Desc MD5
     * @Author liww
     * @Date 2020/2/18
     * @Param [src]
     * @return java.lang.String
     */
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    /**
     * @Desc 加密
     * @Author liww
     * @Date 2020/2/18
     * @Param [beforePass, salt]
     * @return java.lang.String
     */
    public static String beforePassToAfterPass(String beforePass, String salt) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(salt.charAt(0))
                .append(salt.charAt(3))
                .append(beforePass)
                .append(salt.charAt(5))
                .append(salt.charAt(2));
        return md5(stringBuilder.toString());
    }

}
