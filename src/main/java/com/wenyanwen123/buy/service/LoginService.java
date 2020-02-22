package com.wenyanwen123.buy.service;

import com.wenyanwen123.buy.commons.domain.learningdb.User;
import com.wenyanwen123.buy.commons.parameter.rp.login.LoginRp;
import com.wenyanwen123.buy.commons.parameter.rp.login.RegisterRp;
import com.wenyanwen123.buy.commons.response.ResultResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 登陆
 * @Author liww
 * @Date 2020/2/17
 * @Version 1.0
 */
public interface LoginService {

    /**
     * @Desc 注册
     * @Author liww
     * @Date 2020/2/18
     * @Param [param]
     * @return com.wenyanwen123.buy.commons.response.ResultResponse
     */
    ResultResponse register(RegisterRp param);

    /**
     * @Desc 登陆
     * @Author liww
     * @Date 2020/2/17
     * @Param [response, param]
     * @return com.wenyanwen123.buy.commons.response.ResultResponse
     */
    ResultResponse login(HttpServletResponse response, LoginRp param);

    /**
     * @Desc 添加cookie
     * @Author liww
     * @Date 2020/2/21
     * @Param [response, token, user]
     * @return void
     */
    public void addCookie(HttpServletResponse response, String token, User user);

}
