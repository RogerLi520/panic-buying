package com.wenyanwen123.buy.service.impl;

import com.wenyanwen123.buy.commons.domain.learningdb.User;
import com.wenyanwen123.buy.commons.domain.learningdb.UserExample;
import com.wenyanwen123.buy.commons.parameter.rp.login.LoginRp;
import com.wenyanwen123.buy.commons.response.ResultCode;
import com.wenyanwen123.buy.commons.response.ResultResponse;
import com.wenyanwen123.buy.commons.util.EncryptionUtil;
import com.wenyanwen123.buy.commons.util.LogUtil;
import com.wenyanwen123.buy.commons.util.UUIDUtil;
import com.wenyanwen123.buy.dao.learningdb.UserMapper;
import com.wenyanwen123.buy.provider.redis.RedisService;
import com.wenyanwen123.buy.provider.redis.UserKey;
import com.wenyanwen123.buy.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * @Description: TODO
 * @Author liww
 * @Date 2020/2/17
 * @Version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    /**
     * @Desc 登陆
     * @Author liww
     * @Date 2020/2/17
     * @Param [response, param]
     * @return com.wenyanwen123.buy.commons.response.ResultResponse
     */
    @Override
    public ResultResponse login(HttpServletResponse response, LoginRp param) {
        LogUtil.serviceStart(log, "登陆");
        // 查询用户
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andPhoneNumEqualTo(param.getPhoneNum());
        List<User> users = userMapper.selectByExample(userExample);
        if (users != null && users.size() > 0) {
            User user = users.get(0);
            // 验证密码
            String dbPass = user.getPassword();
            String salt = user.getSalt();
            String afterPass = EncryptionUtil.beforePassToAfterPass(param.getPassword(), salt);
            if (!dbPass.equals(afterPass)) {
                return ResultResponse.fail(ResultCode.DEFAULT_FAIL_CODE, "密码错误");
            }
            String token = UUIDUtil.getUuid();
            // 添加cookie
            addCookie(response, token, user);
            return ResultResponse.success(ResultCode.DEFAULT_SUCCESS_CODE, token);
        } else {
            return ResultResponse.fail(ResultCode.DEFAULT_FAIL_CODE, "用户不存在");
        }
    }

    /**
     * @Desc 添加cookie
     * @Author liww
     * @Date 2020/2/18
     * @Param [response, token, user]
     * @return void
     */
    private void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
