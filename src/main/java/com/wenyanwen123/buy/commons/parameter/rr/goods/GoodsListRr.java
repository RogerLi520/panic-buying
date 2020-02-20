package com.wenyanwen123.buy.commons.parameter.rr.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: 商品列表
 * @Author liww
 * @Date 2020/2/20
 * @Version 1.0
 */
@Data
@ApiModel(value = "GoodsListRr", description = "商品列表返回值")
public class GoodsListRr {

    @ApiModelProperty(value = "秒杀商品表ID", name = "id")
    private Long id;

    @ApiModelProperty(value = "商品ID", name = "goodsId")
    private Long goodsId;

    @ApiModelProperty(value = "商品名", name = "goodsName")
    private String goodsName;

    @ApiModelProperty(value = "商品标题", name = "goodsTitle")
    private String goodsTitle;

    @ApiModelProperty(value = "图片", name = "goodsImg")
    private String goodsImg;

    @ApiModelProperty(value = "详情", name = "goodsDetail")
    private String goodsDetail;

    @ApiModelProperty(value = "售价", name = "goodsPrice")
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "秒杀价格", name = "seckillPrice")
    private BigDecimal seckillPrice;

    @ApiModelProperty(value = "库存", name = "stockCount")
    private Integer stockCount;

    @ApiModelProperty(value = "抢购开始时间", name = "startTimestamp")
    private Integer startTimestamp;

    @ApiModelProperty(value = "抢购结束时间", name = "endTimestamp")
    private Integer endTimestamp;

}
