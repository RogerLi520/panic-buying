package com.wenyanwen123.buy.provider.redis.keys;

/**
 * @Desc 秒杀相关键名
 * @Author liww
 * @Date 2020/2/24
 * @Version 1.0
 */
public class SeckillKey extends BasePrefix{

	private SeckillKey(int expireSeconds , String prefix) {
		super(expireSeconds,prefix);
	}

	public static SeckillKey isSellOut = new SeckillKey(0,"so");

	public static SeckillKey seckillPath = new SeckillKey(60,"sp");

	public static SeckillKey verifyCode = new SeckillKey(300, "vc");

}
