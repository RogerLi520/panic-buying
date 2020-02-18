package com.wenyanwen123.buy.controller;

import com.wenyanwen123.buy.commons.parameter.rp.login.LoginRp;
import com.wenyanwen123.buy.commons.response.ResultResponse;
import com.wenyanwen123.buy.commons.util.LogUtil;
import com.wenyanwen123.buy.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Description: TODO
 * @Author liww
 * @Date 2020/2/17
 * @Version 1.0
 */
@Api(value = "登陆")
@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "登陆")
    @ApiResponse(code = 200, message = "ok", response = ResultResponse.class)
    @ResponseBody
    @PostMapping("/login")
    public ResultResponse login(HttpServletResponse response, @RequestBody @Valid LoginRp param) {
        LogUtil.callStart(log, "登陆", null);
        ResultResponse resultResponse = loginService.login(response, param);
        LogUtil.outputResult(log, resultResponse);
        return resultResponse;
    }

}
