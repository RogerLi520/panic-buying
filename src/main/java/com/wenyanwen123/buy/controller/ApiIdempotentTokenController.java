package com.wenyanwen123.buy.controller;

import com.wenyanwen123.buy.common.response.ResultResponse;
import com.wenyanwen123.buy.common.util.LogUtil;
import com.wenyanwen123.buy.service.ApiIdempotentTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 幂等接口token
 * @Author liww
 * @Date 2020/3/3
 * @Version 1.0
 */
@Api(value = "幂等接口token")
@Slf4j
@Controller
@RequestMapping("/api/token")
public class ApiIdempotentTokenController {

    @Autowired
    private ApiIdempotentTokenService apiIdempotentTokenService;

    @ApiOperation(value = "获取幂等接口token")
    @ApiResponse(code = 200, message = "ok", response = ResultResponse.class)
    @ResponseBody
    @GetMapping(value = "/value")
    public ResultResponse getApiToken() {
        LogUtil.callStart(log, "获取幂等接口token");
        ResultResponse resultResponse = apiIdempotentTokenService.getApiToken();
        LogUtil.outputResult(log, resultResponse);
        return resultResponse;
    }

}

