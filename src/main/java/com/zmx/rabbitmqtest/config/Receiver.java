package com.zmx.rabbitmqtest.config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Receiver {

    public void receiveMessage(byte[] messageBytes) {
        receiveMessage(new String(messageBytes));
    }

    public void receiveMessage(String message) {
        log.info("RabbitMq接收到消息：【{}】", message);
    }


}
