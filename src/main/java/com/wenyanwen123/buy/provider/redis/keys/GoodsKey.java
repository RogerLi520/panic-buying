package com.wenyanwen123.buy.provider.redis.keys;

/**
 * @Desc 商品相关的键名
 * @Author liww
 * @Date 2020/2/20
 * @Version 1.0
 */
public class GoodsKey extends BasePrefix {

	public static GoodsKey goodsList = new GoodsKey(60, "gl");

	public static GoodsKey goodsDetail = new GoodsKey(60, "gd");

	public static GoodsKey goodsStock = new GoodsKey(0, "gs");

	private GoodsKey(int expireSeconds, String prefix) { super(expireSeconds, prefix); }

}