package com.wenyanwen123.buy.service.impl;

import com.wenyanwen123.buy.commons.domain.learningdb.SeckillOrder;
import com.wenyanwen123.buy.commons.domain.learningdb.User;
import com.wenyanwen123.buy.commons.parameter.rr.goods.GoodsRr;
import com.wenyanwen123.buy.commons.response.ResultCode;
import com.wenyanwen123.buy.commons.response.ResultResponse;
import com.wenyanwen123.buy.commons.util.BeanUtil;
import com.wenyanwen123.buy.commons.util.LogUtil;
import com.wenyanwen123.buy.commons.util.UUIDUtil;
import com.wenyanwen123.buy.commons.util.security.MD5Util;
import com.wenyanwen123.buy.commons.util.security.VerifyCodeUtil;
import com.wenyanwen123.buy.dao.learningdb.FlashSaleGoodsMapper;
import com.wenyanwen123.buy.dao.learningdb.SeckillOrderMapper;
import com.wenyanwen123.buy.provider.rabbitmq.MQSender;
import com.wenyanwen123.buy.provider.rabbitmq.TopicRabbitConfig;
import com.wenyanwen123.buy.provider.rabbitmq.message.SeckillMessage;
import com.wenyanwen123.buy.provider.redis.RedisService;
import com.wenyanwen123.buy.provider.redis.keys.GoodsKey;
import com.wenyanwen123.buy.provider.redis.keys.SeckillKey;
import com.wenyanwen123.buy.service.FlashSaleService;
import com.wenyanwen123.buy.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * @Description: TODO
 * @Author liww
 * @Date 2020/2/23
 * @Version 1.0
 */
@Service
public class FlashSaleServiceImpl implements FlashSaleService {

    private static final Logger log = LoggerFactory.getLogger(FlashSaleServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private FlashSaleGoodsMapper flashSaleGoodsMapper;

    @Autowired
    private VerifyCodeUtil verifyCodeUtil;

    @Autowired
    private MQSender mqSender;

    private HashMap<Long, Boolean> goodsStockOverMap =  new HashMap<Long, Boolean>(); // 商品库存

    /**
     * @Desc 获取秒杀验证码
     * @Author liww
     * @Date 2020/2/23
     * @Param [response, user, goodsId]
     * @return java.awt.image.BufferedImage
     */
    @Override
    public BufferedImage getSeckillVerifyCod(HttpServletResponse response, User user, long goodsId) {
        LogUtil.serviceStart(log, "获取秒杀验证码");
        VerifyCodeUtil.VerifyCodeResult verifyCodeResult = verifyCodeUtil.createVerifyCode();
        // 将验证码答案存入redis中
        redisService.set(SeckillKey.verifyCode, user.getUserId() + "," + goodsId, verifyCodeResult.getAnswer());
        // 返回验证码图片流
        return verifyCodeResult.getBufferedImage();
    }

    /**
     * @Desc 获取秒杀动态路径
     * @Author liww
     * @Date 2020/2/24
     * @Param [request, user, goodsId, verifyCode]
     * @return com.wenyanwen123.buy.commons.response.ResultResponse
     */
    @Override
    public ResultResponse getSeckillPaht(HttpServletRequest request, User user, long goodsId, long verifyCode) {
        LogUtil.serviceStart(log, "获取秒杀动态路径");
        if(user == null) {
            return ResultResponse.fail(ResultCode.TOKEN_EXCEPTION);
        }
        // 校验验证码
        Integer answers = redisService.get(SeckillKey.verifyCode, user.getUserId() + "," + goodsId, Integer.class);
        if (answers == null || answers - verifyCode != 0) {
            return ResultResponse.fail(ResultCode.VERIFICATION_CODE_ERROR);
        }
        redisService.delete(SeckillKey.verifyCode, user.getUserId() + "," + goodsId);
        // 生成动态路径
        String str = MD5Util.md5(UUIDUtil.getUuid() + "123456");
        redisService.set(SeckillKey.seckillPath, ""+user.getUserId() + "_" + goodsId, str);
        return ResultResponse.success("操作成功", str);
    }

    /**
     * @Desc 秒杀
     * @Author liww
     * @Date 2020/2/24
     * @Param [model, user, goodsId, path]
     * @return com.wenyanwen123.buy.commons.response.ResultResponse
     */
    @Override
    public ResultResponse seckill(Model model, User user, long goodsId, String path) {
        // 验证路径
        if (StringUtils.isEmpty(path)) {
            return ResultResponse.fail(ResultCode.DEFAULT_FAIL_CODE, "请求错误");
        }
        String pathCache = redisService.get(SeckillKey.seckillPath, user.getUserId() + "_" + goodsId, String.class);
        if (!path.equals(pathCache)) {
            return ResultResponse.fail(ResultCode.DEFAULT_FAIL_CODE, "请求错误");
        }
        // 判断是否已秒杀
        SeckillOrder seckillOrder = orderService.getSeckillOrderByUserIdGoodsId(user.getUserId(), goodsId);
        if (seckillOrder != null) {
            return ResultResponse.fail(ResultCode.DEFAULT_FAIL_CODE, "请勿重复秒杀");
        }
        // 判断是否还有库存(线程不安全，需要改进)
        boolean isOver = goodsStockOverMap.get(goodsId);
        if (isOver) {
            return ResultResponse.fail(ResultCode.DEFAULT_FAIL_CODE, "商品已秒杀完毕");
        }
        // 预减库存
        long stock = redisService.decr(GoodsKey.goodsStock, "" + goodsId);
        if(stock < 0) {
            goodsStockOverMap.put(goodsId, true);
            return ResultResponse.fail(ResultCode.DEFAULT_FAIL_CODE, "商品已秒杀完毕");
        }
        // 秒杀成功，交给消息队列处理
        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setGoodsId(goodsId);
        seckillMessage.setUser(user);
        String message = BeanUtil.beanToString(seckillMessage);
        mqSender.sendSeckillMessage("topicExchange", TopicRabbitConfig.seckill, message);
        return null;
    }

    /**
     * @Desc 秒杀结果
     * @Author liww
     * @Date 2020/2/26
     * @Param [model, user, goodsId]
     * @return com.wenyanwen123.buy.commons.response.ResultResponse
     */
    @Override
    public ResultResponse seckillResult(Model model, User user, long goodsId) {
        LogUtil.serviceStart(log, "秒杀结果");
        //判断是否秒杀到了
        SeckillOrder seckillOrder = orderService.getSeckillOrderByUserIdGoodsId(user.getUserId(), goodsId);
        if(seckillOrder != null) {
            // 秒杀成功
            return ResultResponse.success("秒杀成功", seckillOrder.getOrderNum());
        } else {
            boolean isOver = redisService.exists(SeckillKey.isSellOut, "" + goodsId);
            if (isOver) {
                // 售罄
                return ResultResponse.success("已售罄", -1);
            } else {
                // 排队中
                return ResultResponse.success("排队中", 0);
            }
        }
    }

}
