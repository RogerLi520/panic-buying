package com.wenyanwen123.buy.service.impl;

import com.wenyanwen123.buy.commons.domain.learningdb.SeckillOrder;
import com.wenyanwen123.buy.commons.util.LogUtil;
import com.wenyanwen123.buy.provider.redis.RedisService;
import com.wenyanwen123.buy.provider.redis.keys.OrderKey;
import com.wenyanwen123.buy.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Author liww
 * @Date 2020/2/22
 * @Version 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private RedisService redisService;

    /**
     * @Desc 获取缓存中的用户订单
     * @Author liww
     * @Date 2020/2/22
     * @Param [userId, goodsId]
     * @return com.wenyanwen123.buy.commons.domain.learningdb.User
     */
    @Override
    public SeckillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId) {
        LogUtil.serviceStart(log, "获取缓存中的用户订单");
        SeckillOrder seckillOrder = redisService.get(OrderKey.seckillOrder, userId + "_" + goodsId, SeckillOrder.class);
        return seckillOrder;
    }

}
