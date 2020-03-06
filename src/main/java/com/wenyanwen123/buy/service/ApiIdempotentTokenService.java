package com.wenyanwen123.buy.service;

import com.wenyanwen123.buy.common.response.ResultResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @Desc 幂等接口token
 * @Author liww
 * @Date 2020/3/3
 * @Version 1.0
 */
public interface ApiIdempotentTokenService {

    /**
     * @Desc 获取幂等接口token
     * @Author liww
     * @Date 2020/3/3
     * @Param []
     * @return com.wenyanwen123.buy.common.response.ResultResponse
     */
    ResultResponse getApiToken();

    /**
     * @Desc 校验api token
     * @Author liww
     * @Date 2020/3/3
     * @Param [request]
     * @return void
     */
    void checkApiToken(HttpServletRequest request);

}
