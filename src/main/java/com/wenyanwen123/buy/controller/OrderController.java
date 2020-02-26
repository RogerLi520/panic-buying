package com.wenyanwen123.buy.controller;

import com.wenyanwen123.buy.common.domain.learningdb.User;
import com.wenyanwen123.buy.common.response.ResultResponse;
import com.wenyanwen123.buy.common.util.LogUtil;
import com.wenyanwen123.buy.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 订单
 * @Author liww
 * @Date 2020/2/26
 * @Version 1.0
 */
@Api(value = "订单")
@Controller
@RequestMapping("/api/order")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "订单详情")
    @ApiResponse(code = 200, message = "ok", response = ResultResponse.class)
    @ResponseBody
    @GetMapping("/detail")
    public ResultResponse orderDetail(User user, @RequestParam("orderNum") String orderNum) {
        LogUtil.callStart(log, "订单详情");
        ResultResponse resultResponse = orderService.orderDetail(user, orderNum);
        LogUtil.outputResult(log, resultResponse);
        return resultResponse;
    }

}
