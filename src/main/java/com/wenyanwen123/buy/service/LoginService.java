package com.wenyanwen123.buy.service;

import com.wenyanwen123.buy.commons.parameter.rp.login.LoginRp;
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
     * @Desc 登陆
     * @Author liww
     * @Date 2020/2/17
     * @Param [response, param]
     * @return com.wenyanwen123.buy.commons.response.ResultResponse
     */
    ResultResponse login(HttpServletResponse response, LoginRp param);

}
