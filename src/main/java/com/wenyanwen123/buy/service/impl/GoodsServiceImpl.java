package com.wenyanwen123.buy.service.impl;

import com.wenyanwen123.buy.commons.domain.learningdb.User;
import com.wenyanwen123.buy.commons.parameter.rr.goods.GoodsListRr;
import com.wenyanwen123.buy.commons.response.ResultCode;
import com.wenyanwen123.buy.commons.response.ResultResponse;
import com.wenyanwen123.buy.commons.util.LogUtil;
import com.wenyanwen123.buy.dao.learningdb.FlashSaleGoodsMapper;
import com.wenyanwen123.buy.provider.redis.RedisService;
import com.wenyanwen123.buy.provider.redis.keys.GoodsKey;
import com.wenyanwen123.buy.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description: 商品
 * @Author liww
 * @Date 2020/2/20
 * @Version 1.0
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    private static final Logger log = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private FlashSaleGoodsMapper flashSaleGoodsMapper;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    /**
     * @Desc 获取商品列表
     * @Author liww
     * @Date 2020/2/20
     * @Param [request, response, model, user]
     * @return com.wenyanwen123.buy.commons.response.ResultResponse
     */
    @Override
    public String goodsList(HttpServletRequest request, HttpServletResponse response, Model model, User user) {
        LogUtil.serviceStart(log, "获取商品列表");
        if (user == null) {
            return "请先登陆";
        }
        model.addAttribute(user);
        // 取缓存
        String goodsListHtml = redisService.get(GoodsKey.goodsList, "", String.class);
        if(!StringUtils.isEmpty(goodsListHtml)) {
            return goodsListHtml;
        }
        List<GoodsListRr> goodsListRrs = flashSaleGoodsMapper.selectGoodsList();
        model.addAttribute("goodsList", goodsListRrs);
        // 手动渲染
        WebContext springWebContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        goodsListHtml = thymeleafViewResolver.getTemplateEngine().process("goods_list", springWebContext);
        if(StringUtils.isNotEmpty(goodsListHtml)) {
            redisService.set(GoodsKey.goodsList, "", goodsListHtml);
        }
        return goodsListHtml;
    }

}
