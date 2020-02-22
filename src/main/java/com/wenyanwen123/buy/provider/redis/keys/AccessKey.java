package com.wenyanwen123.buy.provider.redis.keys;

/**
 * @Desc 访问相关键名
 * @Author liww
 * @Date 2020/2/21
 * @Version 1.0
 */
public class AccessKey extends BasePrefix {

	private AccessKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	
	// 限制在一定时间内访问的次数
	public static AccessKey withExpire(int expireSeconds) {
		return new AccessKey(expireSeconds, "access");
	}
	
}
