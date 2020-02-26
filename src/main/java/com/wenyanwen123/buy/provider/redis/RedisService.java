package com.wenyanwen123.buy.provider.redis;

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
        T result = (T) redisUtil.get(realKey);
        return result;
    }

    /**
     * @Desc 原子自增
     * @Author liww
     * @Date 2020/2/21
     * @Param [prefix, key]
     * @return java.lang.Long
     */
    public <T> Long incr(KeyPrefix prefix, String key) {
        String realKey  = prefix.getPrefix() + key;
        return redisUtil.incr(realKey, 1);
    }

    /**
     * @Desc 删除
     * @Author liww
     * @Date 2020/2/24
     * @Param [prefix, key]
     * @return boolean
     */
    public void delete(KeyPrefix prefix, String key) {
        String realKey  = prefix.getPrefix() + key;
        redisUtil.del(realKey);
    }

    /**
     * @Desc 减
     * @Author liww
     * @Date 2020/2/24
     * @Param [prefix, key]
     * @return java.lang.Long
     */
    public <T> Long decr(KeyPrefix prefix, String key) {
        String realKey  = prefix.getPrefix() + key;
        return redisUtil.decr(realKey, 1);
    }

    /**
     * @Desc 判断key是否存在
     * @Author liww
     * @Date 2020/2/26
     * @Param [prefix, key]
     * @return boolean
     */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        String realKey  = prefix.getPrefix() + key;
        return  redisUtil.hasKey(realKey);
    }

}
