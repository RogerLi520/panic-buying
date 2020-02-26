package com.wenyanwen123.buy.service.impl;

import com.wenyanwen123.buy.common.domain.learningdb.SeckillOrder;
import com.wenyanwen123.buy.common.domain.learningdb.SeckillOrderSnapshot;
import com.wenyanwen123.buy.common.domain.learningdb.User;
import com.wenyanwen123.buy.common.model.vo.goods.GoodsVO;
import com.wenyanwen123.buy.common.model.vo.order.SeckillOrderDetailVO;
import com.wenyanwen123.buy.common.response.ResultCode;
import com.wenyanwen123.buy.common.response.ResultResponse;
import com.wenyanwen123.buy.common.util.AdapterUtil;
import com.wenyanwen123.buy.common.util.DateUtil;
import com.wenyanwen123.buy.common.util.LogUtil;
import com.wenyanwen123.buy.common.util.UUIDUtil;
import com.wenyanwen123.buy.dao.learningdb.FlashSaleGoodsMapper;
import com.wenyanwen123.buy.dao.learningdb.SeckillOrderMapper;
import com.wenyanwen123.buy.dao.learningdb.SeckillOrderSnapshotMapper;
import com.wenyanwen123.buy.provider.redis.RedisService;
import com.wenyanwen123.buy.provider.redis.keys.OrderKey;
import com.wenyanwen123.buy.provider.redis.keys.SeckillKey;
import com.wenyanwen123.buy.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 订单
 * @Author liww
 * @Date 2020/2/22
 * @Version 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private FlashSaleGoodsMapper flashSaleGoodsMapper;

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private SeckillOrderSnapshotMapper seckillOrderSnapshotMapper;

    /**
     * @Desc 获取缓存中的用户订单
     * @Author liww
     * @Date 2020/2/22
     * @Param [userId, goodsId]
     * @return com.wenyanwen123.buy.common.domain.learningdb.User
     */
    @Override
    public SeckillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId) {
        LogUtil.serviceStart(log, "获取缓存中的用户订单");
        SeckillOrder seckillOrder = redisService.get(OrderKey.seckillOrder, userId + "_" + goodsId, SeckillOrder.class);
        return seckillOrder;
    }

    /**
     * @Desc 创建秒杀订单
     * @Author liww
     * @Date 2020/2/26
     * @Param [user, goods]
     * @return com.wenyanwen123.buy.common.model.vo.order.SeckillOrderInfo
     */
    @Override
    public SeckillOrderDetailVO placeSeckillOrder(User user, GoodsVO goods) {
        LogUtil.serviceStart(log, "下单");
        // 减库存
        int sqlResult = flashSaleGoodsMapper.reduceStock(goods.getGoodsId(), 1);
        if (sqlResult > 0) {
            // 创建订单
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setOrderNum(orderNumFactory());
            seckillOrder.setUserId(user.getUserId());
            seckillOrder.setGoodsId(goods.getGoodsId());
            seckillOrder.setOrderChannel(1);
            seckillOrder.setOrderStatus(1);
            seckillOrder.setCreateTime(DateUtil.getCurrentDate());
            seckillOrder.setCreateTimestamp(DateUtil.getTimeStamp());
            seckillOrder.setIsDel(false);
            seckillOrderMapper.insert(seckillOrder);
            SeckillOrderSnapshot seckillOrderSnapshot = new SeckillOrderSnapshot();
            seckillOrderSnapshot.setOrderNum(seckillOrder.getOrderNum());
            seckillOrderSnapshot.setGoodsCount(1);
            seckillOrderSnapshot.setGoodsPrice(goods.getGoodsPrice());
            seckillOrderSnapshot.setSeckillPrice(goods.getSeckillPrice());
            seckillOrderSnapshot.setGoodsName(goods.getGoodsName());
            seckillOrderSnapshot.setGoodsTitle(goods.getGoodsTitle());
            seckillOrderSnapshot.setGoodsImg(goods.getGoodsImg());
            seckillOrderSnapshot.setGoodsDetail(goods.getGoodsDetail());
            seckillOrderSnapshotMapper.insert(seckillOrderSnapshot);
            // 将订单存入redis缓存中
            redisService.set(OrderKey.seckillOrder, user.getUserId() + "_" + goods.getGoodsId(), seckillOrder);
            // 返回订单信息
            SeckillOrderDetailVO seckillOrderInfo = AdapterUtil.adapter(goods, SeckillOrderDetailVO.class);
            seckillOrderInfo.setUserId(user.getUserId());
            seckillOrderInfo.setOrderNum(seckillOrder.getOrderNum());
            return seckillOrderInfo;
        } else {
            // 缓存中标识已卖完
            redisService.set(SeckillKey.isSellOut, goods.getGoodsId().toString(), true);
            return null;
        }
    }

    /**
     * @Desc 获取订单编号
     * @Author liww
     * @Date 2020/2/26
     * @Param []
     * @return java.lang.String
     */
    private String orderNumFactory() {
        return UUIDUtil.getUuid();
    }

    /**
     * @Desc 订单详情
     * @Author liww
     * @Date 2020/2/26
     * @Param [user, orderNum]
     * @return com.wenyanwen123.buy.common.response.ResultResponse
     */
    @Override
    public ResultResponse orderDetail(User user, String orderNum) {
        LogUtil.serviceStart(log, "订单详情");
        SeckillOrder seckillOrder = seckillOrderMapper.selectOrderByOrderNum(orderNum);
        if (seckillOrder == null) {
            return ResultResponse.fail(ResultCode.DEFAULT_FAIL_CODE, "订单不存在");
        }
        SeckillOrderSnapshot seckillOrderSnapshot = seckillOrderSnapshotMapper.selectByOrderNum(orderNum);
        // 返回订单详情
        SeckillOrderDetailVO seckillOrderDetailVO = AdapterUtil.adapter(seckillOrderSnapshot, SeckillOrderDetailVO.class);
        seckillOrderDetailVO.setUserId(user.getUserId());
        seckillOrderDetailVO.setGoodsId(seckillOrder.getGoodsId());
        seckillOrderDetailVO.setOrderChannel(seckillOrder.getOrderChannel());
        seckillOrderDetailVO.setOrderStatus(seckillOrder.getOrderStatus());
        seckillOrderDetailVO.setCreateTime(seckillOrder.getCreateTime());
        seckillOrderDetailVO.setCreateTimestamp(seckillOrder.getCreateTimestamp());
        return ResultResponse.success(seckillOrderDetailVO);
    }

}
