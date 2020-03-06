package com.wenyanwen123.buy.common.core.interceptor;

import com.wenyanwen123.buy.common.core.annotation.AccessLimit;
import com.wenyanwen123.buy.common.core.annotation.ApiIdempotent;
import com.wenyanwen123.buy.common.core.annotation.IgnoreLogin;
import com.wenyanwen123.buy.common.core.exception.FrequentVisitsException;
import com.wenyanwen123.buy.common.domain.learningdb.User;
import com.wenyanwen123.buy.common.util.IPUtil;
import com.wenyanwen123.buy.provider.redis.RedisService;
import com.wenyanwen123.buy.provider.redis.keys.AccessKey;
import com.wenyanwen123.buy.service.ApiIdempotentTokenService;
import com.wenyanwen123.buy.service.UserService;
import com.wenyanwen123.buy.service.impl.LoginServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @Desc 访问拦截器
 * @Author liww
 * @Date 2020/3/3
 * @Version 1.0
 */
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private RedisService redisService;

	@Autowired
	private UserService userService;

	@Autowired
	private ApiIdempotentTokenService apiIdempotentTokenService;
	
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
		// 判断被拦截的是否是方法
		if(handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			// 获取用户信息
			User user = getUser(request, response);

			// 1. @IgnoreLogin注解
			if (user == null) {
				IgnoreLogin ignoreLogin = handlerMethod.getMethodAnnotation(IgnoreLogin.class);
				if (ignoreLogin == null || !ignoreLogin.ignore()) {
					response.setStatus(HttpStatus.SC_UNAUTHORIZED);
					request.getRequestDispatcher("/api/login/page").forward(request, response);
					return false;
				}
			} else {
				// 将user信息存入自定义容器中
				UserContext.setUser(user);
			}

			// 2. @ApiIdempotent注解
			ApiIdempotent apiIdempotent = handlerMethod.getMethodAnnotation(ApiIdempotent.class);
			if (apiIdempotent != null) {
				apiIdempotentTokenService.checkApiToken(request);
			}

			// 3. @AccessLimit注解
			AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
			if (accessLimit == null) {
				return true;
			}
			accessCountLimit(request, accessLimit, user);
		}
		return true;
	}

	/**
	 * @Desc 访问次数限制
	 * @Author liww
	 * @Date 2020/3/4
	 * @Param [request, accessLimit, user]
	 * @return void
	 */
	public void accessCountLimit(HttpServletRequest request, AccessLimit accessLimit, User user) {
		// 获取注解的参数
		int seconds = accessLimit.seconds();
		int maxAccessCount = accessLimit.maxAccessCount();
		// 限制一定时间内访问的次数
		if (maxAccessCount >= 0) {
			// 获取当前路径
			String key = request.getRequestURI();
			if (user != null) {
				key += "_" + user.getUserId();
			} else {
				key += "_" + IPUtil.getRemoteAddr(request);
			}
			AccessKey accessKey = AccessKey.withExpire(seconds);
			Integer count = redisService.get(accessKey, key, Integer.class);
			if(count == null) {
				// 第一次进来为空，赋值默认值1
				redisService.set(accessKey, key, 1);
			} else if (count < maxAccessCount) {
				// 每访问一次redis存储的值加一
				redisService.incr(accessKey, key);
			} else {
				// 提示访问频繁
				throw new FrequentVisitsException("访问过于频繁，请于" + seconds + "秒后刷新重试!");
			}
		}
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
	 * @return com.wenyanwen123.buy.common.domain.learningdb.User
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
	 * @Param [request, cookieName]
	 * @return java.lang.String
	 */
	private String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length <= 0) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieName)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
}
