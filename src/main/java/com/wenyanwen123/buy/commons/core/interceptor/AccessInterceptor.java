package com.wenyanwen123.buy.commons.core.interceptor;

import com.wenyanwen123.buy.commons.domain.learningdb.User;
import com.wenyanwen123.buy.provider.redis.RedisService;
import com.wenyanwen123.buy.provider.redis.keys.AccessKey;
import com.wenyanwen123.buy.service.LoginService;
import com.wenyanwen123.buy.service.UserService;
import com.wenyanwen123.buy.service.impl.LoginServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private RedisService redisService;

	@Autowired
	private UserService userService;
	
	/**
	 * @Desc 拦截器 @AccessLimit
	 * @Author liww
	 * @Date 2020/2/21
	 * @Param [request, response, handler]
	 * @return boolean
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 判断方法是否被拦截
		if(handler instanceof HandlerMethod) {
			// 获取用户信息
			User user = getUser(request, response);
			// 将user信息存入容器中
			UserContext.setUser(user);
			HandlerMethod hm = (HandlerMethod)handler;
			AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
			if (accessLimit == null) {
				return true;
			}
			// 获取注解的参数
			int seconds = accessLimit.seconds();
			int accessCount = accessLimit.accessCount();
			boolean needLogin = accessLimit.needLogin();
			// 获取当前路径
			String key = request.getRequestURI();
			// 是否要登录
			if (needLogin) {
				if(user == null) {
					render(response, "请先登陆");
					return false;
				}
				key += "_" + user.getUserId();
			} else {
				//do nothing
			}
			// 限制一定时间内访问的次数
			AccessKey accessKey = AccessKey.withExpire(seconds);
			Integer count = redisService.get(accessKey, key, Integer.class);
	    	if(count == null) {
	    		// 第一次进来为空，赋值默认值1
	    		 redisService.set(accessKey, key, 1);
	    	} else if (count < accessCount) {
	    		// 每访问一次redis存储的值加一
	    		 redisService.incr(accessKey, key);
	    	} else {
	    		// 提示访问频繁
	    		render(response, "访问过于频繁");
	    		return false;
	    	}
		}
		return true;
	}
	
	
	/**
	 * @Desc 抛出提示到页面上
	 * @Author liww
	 * @Date 2020/2/21
	 * @Param [response, message]
	 * @return void
	 */
	private void render(HttpServletResponse response, String message)throws Exception {
		// 设置编码utf8
		response.setContentType("application/json;charset=UTF-8");
		OutputStream out = response.getOutputStream();
		out.write(message.getBytes("UTF-8"));
		out.flush();
		out.close();
	}

	
	/**
	 * @Desc 从缓存中获取用户信息
	 * @Author liww
	 * @Date 2020/2/21
	 * @Param [request, response]
	 * @return com.wenyanwen123.buy.commons.domain.learningdb.User
	 */
	private User getUser(HttpServletRequest request, HttpServletResponse response) {
		String paramToken = request.getParameter(LoginServiceImpl.COOKIE_NAME_TOKEN);
		String cookieToken = getCookieValue(request, LoginServiceImpl.COOKIE_NAME_TOKEN);
		if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
			return null;
		}
		// 判断是否空值，然后进行前端cookie和服务端生成cookie对比
		String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
		return userService.getUserByToken(response, token);
	}
	
	/**
	 * @Desc 获取前端传递进来的cookie
	 * @Author liww
	 * @Date 2020/2/21
	 * @Param [request, cookiName]
	 * @return java.lang.String
	 */
	private String getCookieValue(HttpServletRequest request, String cookiName) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length <= 0) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookiName)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
}
