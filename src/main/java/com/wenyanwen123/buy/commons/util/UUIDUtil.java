package com.wenyanwen123.buy.commons.util;


import java.util.UUID;

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
