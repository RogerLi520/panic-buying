package com.wenyanwen123.buy.common.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Desc 访问次数限制
 * @Author liww
 * @Date 2020/2/21
 * @Version 1.0
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface AccessLimit {

	// 和maxAccessCount配合使用，代表seconds时间内，最多访问该接口maxAccessCount次。
	int seconds() default 60;

	// 最多访问次数（默认值为-1，代表访问次数无限制）
	int maxAccessCount() default -1;

}

