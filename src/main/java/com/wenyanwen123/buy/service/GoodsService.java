package com.wenyanwen123.buy.service;

import com.wenyanwen123.buy.commons.domain.learningdb.User;
import com.wenyanwen123.buy.commons.response.ResultResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Desc 商品
 * @Author liww
 * @Date 2020/2/20
 * @Version 1.0
 */
public interface GoodsService {

    /**
     * @Desc 获取商品列表
     * @Author liww
     * @Date 2020/2/20
     * @Param [request, response, model, user]
     * @return com.wenyanwen123.buy.commons.response.ResultResponse
     */
    String goodsList(HttpServletRequest request, HttpServletResponse response, Model model, User user);

    /**
     * @Desc 商品详情
     * @Author liww
     * @Date 2020/2/22
     * @Param [request, response, model, user, goodsId]
     * @return com.wenyanwen123.buy.commons.response.ResultResponse
     */
    ResultResponse goodsDetail(HttpServletRequest request, HttpServletResponse response, Model model, User user, @PathVariable("goodsId")long goodsId);

}
