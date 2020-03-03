package com.wenyanwen123.buy.provider.redis.keys;

/**
 * @Description: api token相关的键名
 * @Author liww
 * @Date 2020/3/3
 * @Version 1.0
 */
public class ApiTokenKey extends BasePrefix {

    public static ApiTokenKey apiTokenKey = new ApiTokenKey(300, "at");

    private ApiTokenKey(int expireSeconds, String prefix) { super(expireSeconds, prefix); }

}
