package com.zmx.rabbitmqtest.config.rabbit.exchange;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

//header exchange(头交换机)和主题交换机有点相似，
// 但是不同于主题交换机的路由是基于路由键，头交换机的路由值基于消息的header数据。
//x-match[all/any] all:所有的header头信息必须匹配
//any:只要一个匹配即可
//direct 只需要匹配一个routing key即可
@Configuration
public class HeadersExchangeConfig {

    @Bean
    public HeadersExchange headersExchange() {
        HeadersExchange headersExchange = new HeadersExchange("zmx-headers");
        return headersExchange;
    }


    @Bean
    public Queue headersQueue1() {
        Queue queue=new Queue("zmx-headersqueue1");
        return queue;
    }

    @Bean
    public Queue headersQueue2() {
        Queue queue=new Queue("zmx-headersqueue2");
        return queue;
    }


    @Bean
    public Binding bindingHeaders1(){
        Map<String,Object> headers = new HashMap<>();
        headers.put("userName","zmx");

        Binding binding= BindingBuilder
                .bind(headersQueue1())
                .to(headersExchange()).whereAny(headers).match();
        return binding;
    }

    @Bean
    public Binding bindingHeaders2(){
        Map<String,Object> headers = new HashMap<>();
        headers.put("userName","tdx");

        Binding binding=BindingBuilder.bind(headersQueue2())
                .to(headersExchange()).whereAll(headers).match();
        return binding;
    }
}
