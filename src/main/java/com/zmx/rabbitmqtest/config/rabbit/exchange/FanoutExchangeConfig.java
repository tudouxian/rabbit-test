package com.zmx.rabbitmqtest.config.rabbit.exchange;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//发布订阅模式的配置,包括两个队列和对应的订阅者,发布者的交换机类型使用fanout(子网广播),
// 两根网线binding用来绑定队列到交换机-适用场景 如 群发信息
@Configuration
public class FanoutExchangeConfig {


    @Bean
    public FanoutExchange fanoutExchange(){
        FanoutExchange fanoutExchange=new FanoutExchange("zmx-fanout");
        return fanoutExchange;
    }

    @Bean
    public Queue faoutQueue1() {
        Queue queue=new Queue("zmx-faoutqueue1");
        return queue;
    }

    @Bean
    public Queue faoutQueue2() {
        Queue queue=new Queue("zmx-faoutqueue2");
        return queue;
    }


    @Bean
    public Binding binding1(){
        Binding binding= BindingBuilder
                .bind(faoutQueue1())
                .to(fanoutExchange());
        return binding;
    }

    @Bean
    public Binding binding2(){
        Binding binding=BindingBuilder.bind(faoutQueue2())
                .to(fanoutExchange());
        return binding;
    }
}
