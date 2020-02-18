package com.wenyanwen123.buy.provider.redis;

/**
 * @Description: 用户相关的键名
 * @Author liww
 * @Date 2020/2/18
 * @Version 1.0
 */
public class UserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    public static UserKey token = new UserKey(TOKEN_EXPIRE, "tk");

    public static UserKey getById = new UserKey(0, "id");

    private UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

}
