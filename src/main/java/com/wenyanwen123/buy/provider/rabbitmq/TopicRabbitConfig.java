package com.wenyanwen123.buy.provider.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 主题交换机
 * @Author liww
 * @Date 2020/2/25
 * @Version 1.0
 */
@Configuration
public class TopicRabbitConfig {

    // 路由
    public final static String seckill = "topic.seckill";

    /**
     * @Desc 秒杀队列
     * @Author liww
     * @Date 2020/2/25
     * @Param []
     * @return org.springframework.amqp.core.Queue
     */
    @Bean
    public Queue seckillQueue() {
        return new Queue(TopicRabbitConfig.seckill);
    }

    /**
     * @Desc 交换机
     * @Author liww
     * @Date 2020/2/25
     * @Param []
     * @return org.springframework.amqp.core.TopicExchange
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }

    /**
     * @Desc 绑定交换机和队列
     * @Author liww
     * @Date 2020/2/25
     * @Param []
     * @return org.springframework.amqp.core.Binding
     */
    @Bean
    Binding bindingExchangeQueue() {
        return BindingBuilder.bind(seckillQueue()).to(exchange()).with(seckill);
    }

}
