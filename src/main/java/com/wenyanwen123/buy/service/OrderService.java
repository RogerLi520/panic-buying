package com.wenyanwen123.buy.service;

import com.wenyanwen123.buy.commons.domain.learningdb.SeckillOrder;

/**
 * @Desc 订单
 * @Author liww
 * @Date 2020/2/22
 * @Version 1.0
 */
public interface OrderService {

    /**
     * @Desc 获取缓存中的用户订单
     * @Author liww
     * @Date 2020/2/22
     * @Param [userId, goodsId]
     * @return com.wenyanwen123.buy.commons.domain.learningdb.User
     */
    SeckillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId);

}
