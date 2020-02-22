package com.wenyanwen123.buy.controller;

import com.wenyanwen123.buy.commons.domain.learningdb.User;
import com.wenyanwen123.buy.commons.response.ResultCode;
import com.wenyanwen123.buy.commons.response.ResultResponse;
import com.wenyanwen123.buy.commons.util.LogUtil;
import com.wenyanwen123.buy.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: TODO
 * @Author liww
 * @Date 2020/2/20
 * @Version 1.0
 */
@Api(value = "商品")
@Controller
@RequestMapping("api/goods")
public class GoodsController {

    private static final Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsService goodsService;

    @ApiOperation(value = "获取商品列表")
    @ApiResponse(code = 200, message = "ok", response = ResultResponse.class)
    @ResponseBody
    @GetMapping(value = "/list", produces="text/html")
    public String goodsList(HttpServletRequest request, HttpServletResponse response, Model model, User user) {
        LogUtil.callStart(log, "获取商品列表", user.getUserId());
        String goodsList = goodsService.goodsList(request, response, model, user);
        return goodsList;
    }

}
