package com.zmx.rabbitmqtest.config.rabbit.listener;

import com.zmx.rabbitmqtest.domain.dto.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
*   @Author: 土豆仙
*   @Date: 2021/11/6 16:24
*   @Description:
*/
@Component
@Slf4j
public class FaoutListener2 {
	@RabbitListener(queues = "zmx-faoutqueue2")
	public void subscribe(Mail mail) throws IOException {
		log.info("订阅者2收到消息：【{}】",mail.toString());
	}
}
