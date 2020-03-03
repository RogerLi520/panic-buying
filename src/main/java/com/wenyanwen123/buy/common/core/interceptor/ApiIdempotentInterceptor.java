package com.wenyanwen123.buy.common.core.interceptor;

import com.wenyanwen123.buy.common.core.annotation.ApiIdempotent;
import com.wenyanwen123.buy.service.ApiIdempotentTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Description: 幂等接口拦截器
 * @Author liww
 * @Date 2020/3/3
 * @Version 1.0
 */
@Component
public class ApiIdempotentInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ApiIdempotentTokenService apiIdempotentTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        ApiIdempotent apiIdempotent = method.getAnnotation(ApiIdempotent.class);
        if (apiIdempotent != null) {
            apiIdempotentTokenService.checkApiToken(request);
        }
        return true;
    }

}
