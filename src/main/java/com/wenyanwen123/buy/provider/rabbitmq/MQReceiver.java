package com.wenyanwen123.buy.provider.rabbitmq;

import com.wenyanwen123.buy.commons.domain.learningdb.Goods;
import com.wenyanwen123.buy.commons.domain.learningdb.SeckillOrder;
import com.wenyanwen123.buy.commons.domain.learningdb.User;
import com.wenyanwen123.buy.commons.parameter.rr.goods.GoodsDetailRr;
import com.wenyanwen123.buy.commons.parameter.rr.goods.GoodsRr;
import com.wenyanwen123.buy.commons.util.BeanUtil;
import com.wenyanwen123.buy.dao.learningdb.FlashSaleGoodsMapper;
import com.wenyanwen123.buy.dao.learningdb.SeckillOrderMapper;
import com.wenyanwen123.buy.provider.rabbitmq.message.SeckillMessage;
import com.wenyanwen123.buy.provider.redis.RedisService;
import com.wenyanwen123.buy.service.FlashSaleService;
import com.wenyanwen123.buy.service.GoodsService;
import com.wenyanwen123.buy.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc 消费者
 * @Author liww
 * @Date 2020/2/25
 * @Version 1.0
 */
@Service
public class MQReceiver {

	private static final Logger log = LoggerFactory.getLogger(MQReceiver.class);

	@Autowired
	private RedisService redisService;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private SeckillOrderMapper seckillOrderMapper;

	@Autowired
	private FlashSaleGoodsMapper flashSaleGoodsMapper;


	/**
	 * @Desc 消费秒杀队列
	 * @Author liww
	 * @Date 2020/2/25
	 * @Param [message]
	 * @return void
	 */
	@RabbitListener(queues = TopicRabbitConfig.seckill)
	public void seckillReceiver(String message) {
		log.info("receive message:" + message);
		// String转换成Bean对象
		SeckillMessage seckillMessage = BeanUtil.stringToBean(message, SeckillMessage.class);
		User user = seckillMessage.getUser();
		long goodsId = seckillMessage.getGoodsId();
		// 判断是否已经秒杀到了
		SeckillOrder order = seckillOrderMapper.getSeckillOrderByUserIdGoodsId(user.getUserId(), goodsId);
		if(order != null) {
			return;
		}
		// 查询商品信息
		GoodsRr goods = flashSaleGoodsMapper.selectGoodsDetail(goodsId);
		// 判断是否还有库存
		int stock = goods.getStockCount();
		if(stock <= 0) {
			return;
		}
		// 减库存 下订单 写入秒杀订单
		orderService.placeSeckillOrder(user, goods);
	}

}
