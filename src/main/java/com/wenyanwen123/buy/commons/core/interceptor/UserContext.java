package com.wenyanwen123.buy.commons.core.interceptor;

import com.wenyanwen123.buy.commons.domain.learningdb.User;

/**
 * @Desc 用户上下文
 * @Author liww
 * @Date 2020/2/21
 * @Version 1.0
 */
public class UserContext {
	
	private static ThreadLocal<User> userHolder = new ThreadLocal<User>();
	
	public static void setUser(User user) {
		userHolder.set(user);
	}
	
	public static User getUser() {
		return userHolder.get();
	}

}
