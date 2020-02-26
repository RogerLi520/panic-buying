package com.wenyanwen123.buy.service;

import com.wenyanwen123.buy.common.domain.learningdb.SeckillOrder;
import com.wenyanwen123.buy.common.domain.learningdb.User;
import com.wenyanwen123.buy.common.model.vo.goods.GoodsVO;
import com.wenyanwen123.buy.common.model.vo.order.SeckillOrderDetailVO;
import com.wenyanwen123.buy.common.response.ResultResponse;
import org.springframework.web.bind.annotation.RequestParam;

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
     * @return com.wenyanwen123.buy.common.domain.learningdb.User
     */
    SeckillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId);

    /**
     * @Desc 创建秒杀订单
     * @Author liww
     * @Date 2020/2/26
     * @Param [user, goods]
     * @return com.wenyanwen123.buy.common.model.vo.order.SeckillOrderInfo
     */
    SeckillOrderDetailVO placeSeckillOrder(User user, GoodsVO goods);

    /**
     * @Desc 订单详情
     * @Author liww
     * @Date 2020/2/26
     * @Param [user, orderNum]
     * @return com.wenyanwen123.buy.common.response.ResultResponse
     */
    ResultResponse orderDetail(User user, @RequestParam("orderId") String orderNum);

}
