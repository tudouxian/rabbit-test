package com.zmx.rabbitmqtest.config.rabbit.listener;

import com.zmx.rabbitmqtest.domain.dto.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HeadersListener2 {

    @RabbitListener(queues = "zmx-headersqueue2")
    public void displayMail(Mail mail) throws Exception {
        log.info("headersqueue2队列监听器1号收到消息:【{}】", mail.toString());
    }
}
