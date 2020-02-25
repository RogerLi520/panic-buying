package com.wenyanwen123.buy.provider.rabbitmq.message;

import com.wenyanwen123.buy.commons.domain.learningdb.User;
import lombok.Data;

/**
 * @Description: 秒杀消息
 * @Author liww
 * @Date 2020/2/25
 * @Version 1.0
 */
@Data
public class SeckillMessage {

    private Long goodsId;

    private User user;

}
