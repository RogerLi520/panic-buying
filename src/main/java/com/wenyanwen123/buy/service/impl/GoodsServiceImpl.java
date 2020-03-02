package com.wenyanwen123.buy.service.impl;

import com.wenyanwen123.buy.common.domain.learningdb.SeckillOrder;
import com.wenyanwen123.buy.common.domain.learningdb.User;
import com.wenyanwen123.buy.common.model.vo.goods.GoodsDetailVO;
import com.wenyanwen123.buy.common.model.vo.goods.GoodsVO;
import com.wenyanwen123.buy.common.response.ResultCode;
import com.wenyanwen123.buy.common.response.ResultResponse;
import com.wenyanwen123.buy.common.util.DateUtil;
import com.wenyanwen123.buy.common.util.LogUtil;
import com.wenyanwen123.buy.dao.learningdb.FlashSaleGoodsMapper;
import com.wenyanwen123.buy.dao.learningdb.GoodsMapper;
import com.wenyanwen123.buy.provider.redis.RedisService;
import com.wenyanwen123.buy.provider.redis.keys.GoodsKey;
import com.wenyanwen123.buy.service.FlashSaleService;
import com.wenyanwen123.buy.service.GoodsService;
import com.wenyanwen123.buy.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.context.WebContext;
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
    private OrderService orderService;

    @Autowired
    private FlashSaleService flashSaleService;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private FlashSaleGoodsMapper flashSaleGoodsMapper;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    /**
     * @Desc 获取商品列表
     * @Author liww
     * @Date 2020/2/20
     * @Param [request, response, model, user]
     * @return com.wenyanwen123.buy.common.response.ResultResponse
     */
    @Override
    public String goodsList(HttpServletRequest request, HttpServletResponse response, Model model, User user) {
        LogUtil.serviceStart(log, "获取商品列表");
        model.addAttribute(user);
        // 取缓存
        String goodsListHtml = redisService.get(GoodsKey.goodsList, "", String.class);
        if(!StringUtils.isEmpty(goodsListHtml)) {
            return goodsListHtml;
        }
        List<GoodsVO> goodsListRrs = flashSaleGoodsMapper.selectGoodsList();
        model.addAttribute("goodsList", goodsListRrs);
        // 手动渲染
        WebContext springWebContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        goodsListHtml = thymeleafViewResolver.getTemplateEngine().process("goods_list", springWebContext);
        if(StringUtils.isNotEmpty(goodsListHtml)) {
            redisService.set(GoodsKey.goodsList, "", goodsListHtml);
        }
        return goodsListHtml;
    }

    /**
     * @Desc 商品详情
     * @Author liww
     * @Date 2020/2/22
     * @Param [request, response, model, user, goodsId]
     * @return com.wenyanwen123.buy.common.response.ResultResponse
     */
    @Override
    public ResultResponse goodsDetail(HttpServletRequest request, HttpServletResponse response, Model model, User user, long goodsId) {
        LogUtil.serviceStart(log, "商品详情");
        GoodsDetailVO goodsDetailRr = new GoodsDetailVO();
        GoodsVO goodsRr = flashSaleGoodsMapper.selectGoodsDetail(goodsId);
        SeckillOrder seckillOrder = orderService.getSeckillOrderByUserIdGoodsId(user.getUserId(), goodsId);
        Integer startTime = goodsRr.getStartTimestamp();
        Integer endTime = goodsRr.getEndTimestamp();
        Integer now = DateUtil.getTimeStamp();
        int seckillStatus = 0;
        int remainSeconds = 0;
        if (now < startTime) {
            // 倒计时
            seckillStatus = 0;
            remainSeconds = (int) ((startTime - now ));
        } else if (now > endTime) {
            // 结束
            seckillStatus = 2;
            remainSeconds = -1;
        } else {
            // 进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }
        goodsDetailRr.setGoodsRr(goodsRr);
        goodsDetailRr.setUser(user);
        goodsDetailRr.setSeckillStatus(seckillStatus);
        goodsDetailRr.setRemainSeconds(remainSeconds);
        if (seckillOrder == null) {
            goodsDetailRr.setPurchased(false);
        }else {
            goodsDetailRr.setPurchased(true);
        }
        return ResultResponse.success(goodsDetailRr);
    }

    /**
     * @Desc 初始化秒杀商品库存
     * @Author liww
     * @Date 2020/3/1
     * @Param []
     * @return com.wenyanwen123.buy.common.response.ResultResponse
     */
    @Override
    public ResultResponse initGoodsStock() {
        LogUtil.serviceStart(log, "初始化秒杀商品库存");
        flashSaleService.initGoodsStock();
        return ResultResponse.success("初始化完成");
    }

}
