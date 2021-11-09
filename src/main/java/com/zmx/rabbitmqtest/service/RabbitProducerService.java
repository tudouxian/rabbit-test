package com.zmx.rabbitmqtest.service;

import com.zmx.rabbitmqtest.domain.dto.Mail;

public interface RabbitProducerService {

    public void sendMail(String exchange, String routingkey, Mail mail);//向队列queue发送消息
}
