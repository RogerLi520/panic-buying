package com.wenyanwen123.buy.service.impl;

import com.wenyanwen123.buy.common.domain.learningdb.User;
import com.wenyanwen123.buy.provider.redis.RedisService;
import com.wenyanwen123.buy.provider.redis.keys.UserKey;
import com.wenyanwen123.buy.service.LoginService;
import com.wenyanwen123.buy.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description: TODO
 * @Author liww
 * @Date 2020/2/21
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private LoginService loginService;

    /**
     * @Desc 根据Token获取用户信息
     * @Author liww
     * @Date 2020/2/21
     * @Param [response, token]
     * @return com.wenyanwen123.buy.common.domain.learningdb.User
     */
    @Override
    public User getUserByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        User user = redisService.get(UserKey.token, token, User.class);
        //延长有效期
        if(user != null) {
            loginService.addCookie(response, token, user);
        }
        return user;
    }

}
