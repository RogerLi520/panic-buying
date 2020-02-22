package com.wenyanwen123.buy.provider.redis.keys;

/**
 * @Desc 订单相关的键名
 * @Author liww
 * @Date 2020/2/22
 * @Version 1.0
 */
public class OrderKey extends BasePrefix {

	public OrderKey(String prefix) {
		super(prefix);
	}

	public static OrderKey seckillOrder = new OrderKey("so");

}
