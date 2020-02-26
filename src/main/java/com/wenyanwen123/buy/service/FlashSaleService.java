package com.wenyanwen123.buy.service;

import com.wenyanwen123.buy.commons.domain.learningdb.User;
import com.wenyanwen123.buy.commons.parameter.rr.goods.GoodsRr;
import com.wenyanwen123.buy.commons.response.ResultResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * @Desc 抢购
 * @Author liww
 * @Date 2020/2/23
 * @Version 1.0
 */
public interface FlashSaleService {

    /**
     * @Desc 获取秒杀验证码
     * @Author liww
     * @Date 2020/2/23
     * @Param [response, user, goodsId]
     * @return java.awt.image.BufferedImage
     */
    BufferedImage getSeckillVerifyCod(HttpServletResponse response, User user, long goodsId);

    /**
     * @Desc 获取动态秒杀路径
     * @Author liww
     * @Date 2020/2/24
     * @Param [request, user, goodsId, verifyCode]
     * @return com.wenyanwen123.buy.commons.response.ResultResponse
     */
    ResultResponse getSeckillPaht(HttpServletRequest request, User user, long goodsId, long verifyCode);

    /**
     * @Desc 秒杀
     * @Author liww
     * @Date 2020/2/24
     * @Param [model, user, goodsId, path]
     * @return com.wenyanwen123.buy.commons.response.ResultResponse
     */
    ResultResponse seckill(Model model, User user, long goodsId, String path);

    /**
     * @Desc 秒杀结果
     * @Author liww
     * @Date 2020/2/26
     * @Param [model, user, goodsId]
     * @return com.wenyanwen123.buy.commons.response.ResultResponse
     */
    ResultResponse seckillResult(Model model, User user, long goodsId);

}