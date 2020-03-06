package com.wenyanwen123.buy.controller;

import com.wenyanwen123.buy.common.core.annotation.AccessLimit;
import com.wenyanwen123.buy.common.core.annotation.ApiIdempotent;
import com.wenyanwen123.buy.common.core.annotation.IgnoreLogin;
import com.wenyanwen123.buy.common.domain.learningdb.User;
import com.wenyanwen123.buy.common.response.ResultResponse;
import com.wenyanwen123.buy.common.util.LogUtil;
import com.wenyanwen123.buy.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @IgnoreLogin
    @ResponseBody
    @GetMapping(value = "/list", produces="text/html")
    public String goodsList(HttpServletRequest request, HttpServletResponse response, Model model, User user) {
        LogUtil.callStart(log, "获取商品列表");
        String goodsList = goodsService.goodsList(request, response, model, user);
        return goodsList;
    }

    @ApiOperation(value = "获取商品详情")
    @ApiResponse(code = 200, message = "ok", response = ResultResponse.class)
    @ResponseBody
    @GetMapping(value = "/detail/{goodsId}")
    public ResultResponse goodsDetail(HttpServletRequest request, HttpServletResponse response, Model model, User user, @PathVariable("goodsId")long goodsId) {
        LogUtil.callStart(log, "获取商品详情");
        ResultResponse resultResponse = goodsService.goodsDetail(request, response, model, user, goodsId);
        LogUtil.outputResult(log, resultResponse);
        return resultResponse;
    }

    @ApiOperation(value = "初始化秒杀商品库存")
    @ApiResponse(code = 200, message = "ok", response = ResultResponse.class)
    @ResponseBody
    @GetMapping(value = "/init/stock")
    public ResultResponse initGoodsStock() {
        LogUtil.callStart(log, "初始化秒杀商品库存");
        ResultResponse resultResponse = goodsService.initGoodsStock();
        LogUtil.outputResult(log, resultResponse);
        return resultResponse;
    }

}
