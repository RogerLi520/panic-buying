package com.wenyanwen123.buy.provider.redis;

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

}
