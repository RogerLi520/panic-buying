package com.wenyanwen123.buy.commons.parameter.rr.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 秒杀订单
 * @Author liww
 * @Date 2020/2/26
 * @Version 1.0
 */
@Data
public class SeckillOrderInfo {

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
