package com.wenyanwen123.buy.common.model.vo.goods;

import com.wenyanwen123.buy.common.domain.learningdb.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 商品详情页
 * @Author liww
 * @Date 2020/2/22
 * @Version 1.0
 */
@Data
@ApiModel(value = "GoodsDetailVO", description = "商品详情页返回值")
public class GoodsDetailVO {

    @ApiModelProperty(value = "秒杀状态", name = "seckillStatus")
    private int seckillStatus;

    @ApiModelProperty(value = "", name = "remainSeconds")
    private int remainSeconds;

    @ApiModelProperty(value = "是否已购买", name = "purchased")
    private Boolean purchased;

    @ApiModelProperty(value = "商品信息", name = "goodsRr")
    private GoodsVO goodsRr;

    @ApiModelProperty(value = "用户", name = "user")
    private User user;

}
