package com.wenyanwen123.buy.common.model.vo.order;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 秒杀订单详情
 * @Author liww
 * @Date 2020/2/26
 * @Version 1.0
 */
@Data
@ApiModel(value = "SeckillOrderDetailVO", description = "秒杀订单详情返回值")
public class SeckillOrderDetailVO {

    private Long seckillId;

    private String orderNum;

    private Long userId;

    private Long goodsId;

    private Integer orderChannel;

    private Integer orderStatus;

    private Date createTime;

    private Integer createTimestamp;

    private Date payDate;

    private Integer payTimestamp;

    private Boolean isDel;

    private Long snapshotId;

    private Integer goodsCount;

    private Double goodsPrice;

    private BigDecimal seckillPrice;

    private String goodsName;

    private String goodsTitle;

    private String goodsImg;

    private String goodsDetail;

}
