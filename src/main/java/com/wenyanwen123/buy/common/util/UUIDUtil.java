package com.wenyanwen123.buy.common.util;

import java.util.UUID;

/**
 * @Desc 生成随机数
 * @Author liww
 * @Date 2020/2/18
 * @Version 1.0
 */
public class UUIDUtil {

    /**
     * 生成GUID随机数
     *
     * @return
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}