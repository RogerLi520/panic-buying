package com.wenyanwen123.buy.service;

import com.wenyanwen123.buy.common.domain.learningdb.User;

import javax.servlet.http.HttpServletResponse;

public interface UserService {

    /**
     * @Desc 根据Token获取用户信息
     * @Author liww
     * @Date 2020/2/21
     * @Param [response, token]
     * @return com.wenyanwen123.buy.common.domain.learningdb.User
     */
    User getUserByToken(HttpServletResponse response, String token);

}
