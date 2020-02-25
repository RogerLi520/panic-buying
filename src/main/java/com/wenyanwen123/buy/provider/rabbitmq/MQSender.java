package com.wenyanwen123.buy.provider.rabbitmq;

import com.wenyanwen123.buy.controller.GoodsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc 发送消息
 * @Author liww
 * @Date 2020/2/25
 * @Version 1.0
 */
@Service
public class MQSender {

	private static final Logger log = LoggerFactory.getLogger(MQSender.class);
	
	@Autowired
    AmqpTemplate amqpTemplate;
	
	/**
	 * @Desc 生产者
	 * @Author liww
	 * @Date 2020/2/25
	 * @Param [exchange, routingKey, message]
	 * @return void
	 */
	public void sendSeckillMessage(String exchange, String routingKey, String message) {
		amqpTemplate.convertAndSend(exchange, routingKey, message);
	}

}
