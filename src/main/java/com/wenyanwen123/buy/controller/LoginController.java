package com.wenyanwen123.buy.controller;

import com.wenyanwen123.buy.common.core.annotation.IgnoreLogin;
import com.wenyanwen123.buy.common.model.dto.login.LoginDTO;
import com.wenyanwen123.buy.common.model.dto.login.RegisterDTO;
import com.wenyanwen123.buy.common.response.ResultResponse;
import com.wenyanwen123.buy.common.util.LogUtil;
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
@RequestMapping("/api/login")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "登陆页面")
    @ApiResponse(code = 200, message = "ok", response = String.class)
    @GetMapping("/page")
    public String loginPage() {
        return "login";
    }

    @ApiOperation(value = "注册")
    @ApiResponse(code = 200, message = "ok", response = ResultResponse.class)
    @IgnoreLogin
    @ResponseBody
    @PostMapping("/register")
    public ResultResponse register(@Valid RegisterDTO param) {
        LogUtil.callStart(log, "注册");
        ResultResponse resultResponse = loginService.register(param);
        LogUtil.outputResult(log, resultResponse);
        return resultResponse;
    }

    @ApiOperation(value = "登陆")
    @ApiResponse(code = 200, message = "ok", response = ResultResponse.class)
    @IgnoreLogin
    @ResponseBody
    @PostMapping("/login")
    public ResultResponse login(HttpServletResponse response, @Valid LoginDTO param) {
        LogUtil.callStart(log, "登陆");
        ResultResponse resultResponse = loginService.login(response, param);
        LogUtil.outputResult(log, resultResponse);
        return resultResponse;
    }

}
