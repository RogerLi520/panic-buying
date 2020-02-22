package com.wenyanwen123.buy.commons.core.interceptor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Desc 访问控制注解
 * @Author liww
 * @Date 2020/2/21
 * @Version 1.0
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {

	// 存活时间
	int seconds();

	// 访问次数
	int accessCount();

	// 是否需要登录
	boolean needLogin() default true;

}
