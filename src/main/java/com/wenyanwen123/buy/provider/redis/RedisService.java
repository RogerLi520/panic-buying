package com.wenyanwen123.buy.provider.redis;

import com.alibaba.fastjson.JSON;
import com.wenyanwen123.buy.provider.redis.keys.KeyPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: redis服务
 * @Author liww
 * @Date 2020/2/18
 * @Version 1.0
 */
@Service
public class RedisService {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * @Desc 设置值
     * @Author liww
     * @Date 2020/2/18
     * @Param [prefix, key, value]
     * @return boolean
     */
    public boolean set(KeyPrefix prefix, String key, Object value) {
        String realKey = prefix.getPrefix() + key;
        int time = prefix.expireSeconds();
        boolean result = redisUtil.set(realKey, value, time);
        return result;
    }

    /**
     * @Desc 获取值
     * @Author liww
     * @Date 2020/2/20
     * @Param [prefix, key, clazz]
     * @return T
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        String realKey = prefix.getPrefix() + key;
        String str = redisUtil.get(realKey).toString();
        T result =  stringToBean(str, clazz);
        return result;
    }

    /**
     * @Desc 类型转换
     * @Author liww
     * @Date 2020/2/20
     * @Param [str, clazz]
     * @return T
     */
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

}
