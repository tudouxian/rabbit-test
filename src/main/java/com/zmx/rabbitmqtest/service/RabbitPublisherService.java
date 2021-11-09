package com.zmx.rabbitmqtest.service;

import com.zmx.rabbitmqtest.domain.dto.Mail;

import java.util.Map;

public interface RabbitPublisherService {

    //使用fanout交换机发布消息给所有队列
    public void publishMail(Mail mail);

    //使用direct交换机发送消息
    public void senddirectMail(Mail mail,String routingkey);

    //使用topic交换机发送消息
    public void sendtopicMail(Mail mail,String routingkey);

    //使用header交换机发送消息
    public void sendHeadersMail(Mail mail, Map<String,Object> headers);
}
