package com.wenyanwen123.buy.provider.redis.keys;

/**
 * @Desc 键名前缀
 * @Author liww
 * @Date 2020/2/18
 * @Version 1.0
 */
public interface KeyPrefix {
		
	public int expireSeconds();
	
	public String getPrefix();
	
}
