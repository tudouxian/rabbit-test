package com.zmx.rabbitmqtest.config.rabbit.exchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
*   @Author: 土豆仙
*   @Date: 2021/11/6 15:39
*   @Description: 自定义交换机-延迟队列 -插件实现
 *   等待一段时间之后取出消费。
 *   eg: 1.网上商城下订单后30分钟后没有完成支付，取消订单(如：淘宝、去哪儿网)
 *       2.系统创建了预约之后，需要在预约时间到达前一小时提醒被预约的双方参会
 *       3.系统中的业务失败之后，需要重试
*/
@Configuration
public class DelayPluginsExchangeConfig {

    /**
     * 延时队列交换机
     * 注意这里的交换机类型：CustomExchange
     *
     * @return
     */
  /*  @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        //属性参数 交换机名称 交换机类型 是否持久化 是否自动删除 配置参数   ----需要安装延时队列插件
        return new CustomExchange("delay_exchange",
                "x-delayed-message",
                true,
                false,
                args);
    }*/


    /**
     * 延时队列
     *
     * @return
     */
   /* @Bean
    public Queue delayQueue() {
        //属性参数 队列名称 是否持久化
        return new Queue("zmx-delay_queue", true);
    }*/

    /**
     * 给延时队列绑定交换机
     *
     * @return
     */
   /* @Bean
    public Binding binding() {
        return BindingBuilder.bind(delayQueue())
                .to(delayExchange())
                .with("delay_key")
                .noargs();
    }*/

}
