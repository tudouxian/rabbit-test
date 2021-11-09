package com.zmx.rabbitmqtest.config.rabbit.exchange;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
*   @Author: 土豆仙
*   @Date: 2021/11/7 18:57
*   @Description: 死信队列：DLX
 *   利用DLX，当消息在一个队列中变成死信 (dead message) 之后，
 *   它能被重新publish到另一个Exchange，这个Exchange就是DLX
 *   1.消息被拒绝(basic.reject / basic.nack)，并且requeue = false
 *   2.消息TTL过期
 *   3.队列达到最大长度
*/
@Configuration
public class DeadLetterExchangeConfig {

    /**
     * 死信交换机
     *
     * @return
     */
    @Bean
    public DirectExchange deadLetterDelayExchange() {
        return new DirectExchange("zmx.delay-exchange");
    }

    /**
     * 死信队列
     *消息在过期、requeue、 队列在达到最大长度时，消息就可以直接路由到死信队列
     * @return
     */
    @Bean
    public Queue deadLetterDelayQueue() {
        Map<String, Object> map = new HashMap<>(16);
        map.put("x-dead-letter-exchange", "zmx.receive_exchange");
        map.put("x-dead-letter-routing-key", "zmx.receive_key");
        return new Queue("zmx.delay-queue",
                true,
                false,
                false,
                map);
    }

    /**
     * 给死信队列绑定交换机
     *
     * @return
     */
    @Bean
    public Binding bindingDeadLetter() {
        return BindingBuilder.bind(deadLetterDelayQueue())
                .to(deadLetterDelayExchange())
                .with("zmx.delay_key");
    }



}
