package com.zmx.rabbitmqtest.config.rabbit.exchange;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
*   @Author: 土豆仙
*   @Date: 2021/11/6 15:39
*   @Description: 自定义交换机-延迟队列
 *   等待一段时间之后取出消费。
 *   eg: 1.网上商城下订单后30分钟后没有完成支付，取消订单(如：淘宝、去哪儿网)
 *       2.系统创建了预约之后，需要在预约时间到达前一小时提醒被预约的双方参会
 *       3.系统中的业务失败之后，需要重试
*/
@Configuration
public class DelayExchangeConfig {

    /**
     * 延时队列交换机
     * 注意这里的交换机类型：DirectExchange
     *
     * @return
     */
    @Bean
    public DirectExchange delayExchange5s(){
        DirectExchange directExchange=new DirectExchange("zmx-delay-exchange-5s",
                true,false,null);
        return directExchange;
    }


    /**
     * 延时队列
     *
     * @return
     */
    @Bean
    public Queue delayQueue5s() {
        //属性参数 队列名称 是否持久化
        return new Queue("zmx-delay-queue-5s", true);
    }

    /**
     * 给延时队列绑定交换机
     *
     * @return
     */
    @Bean
    public Binding bindingDelay5s() {
        return BindingBuilder.bind(delayQueue5s())
                .to(delayExchange5s())
                .with("q5s");
    }



    //延时工作队列
    /**
     * 延时队列交换机
     * 注意这里的交换机类型：DirectExchange
     *
     * @return
     */
    @Bean
    public DirectExchange delayWorkExchange5s(){

        DirectExchange directExchange=new DirectExchange("zmx-delay-work-exchange",
                true,
                false,null);
        return directExchange;
    }

    /**
     * 延时队列
     *
     * @return
     */
    @Bean
    public Queue delayWorkQueue5s() {
        Map<String, Object> arguments=new HashMap<String, Object>();
        arguments.put("x-message-ttl" , 5000);//设置消息有效期5秒，过期后变成私信消息，然后进入DLX
        arguments.put("x-dead-letter-exchange" , "zmx-delay-exchange-5s");//设置DLX
        arguments.put("x-dead-letter-routing-key", "q5s");

        //属性参数 队列名称 是否持久化
        return new Queue("zmx-delay-work-queue-5s", true,false,false,arguments);
    }

    /**
     * 给延时队列绑定交换机
     *
     * @return
     */
    @Bean
    public Binding workQueueBinding() {
        return BindingBuilder.bind(delayWorkQueue5s())
                .to(delayWorkExchange5s())
                .with("q5s");
    }


}
