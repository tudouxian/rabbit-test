package com.zmx.rabbitmqtest.config.rabbit.listener;

import com.rabbitmq.client.Channel;
import com.zmx.rabbitmqtest.domain.dto.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static org.springframework.amqp.core.AcknowledgeMode.MANUAL;
import static org.springframework.amqp.core.ExchangeTypes.HEADERS;


@Component
@Slf4j
public class HeadersListener1 {
    @RabbitListener(queues = "zmx-headersqueue1")
    public void displayMail(Mail mail) throws Exception {
        log.info("headersqueue1队列监听器1号收到消息:【{}】", mail.toString());
    }


    //注解式绑定
    @RabbitListener(ackMode = "MANUAL",bindings = @QueueBinding(
            value = @Queue(value = "zmx-headersqueue3",durable = "true"),
            exchange = @Exchange(value = "zmx-headers",durable = "true",type =HEADERS )
    ))
    public void displayMail3(@Payload Mail mail,
                             @Header("userName")String userName,
                             @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                             Channel channel) throws Exception {
        log.info("headersqueue3队列监听器1号收到消息:【{}】"+"=====请求头userName:【{}】", mail.toString(),userName);

        // delivery_tag是消息投递序号，每个channel对应一个(long类型)，
        // 从1开始到9223372036854775807范围，
        // 在手动消息确认时可以对指定delivery_tag的消息进行ack、nack、reject等操作。
        log.info("DELIVERY_TAG标识：【{}】",deliveryTag);


        //制造异常
        //int a = 1/0;

        channel.basicAck(deliveryTag,false);
    }
}
