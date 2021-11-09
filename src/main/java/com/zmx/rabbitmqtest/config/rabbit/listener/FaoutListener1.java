package com.zmx.rabbitmqtest.config.rabbit.listener;

import com.zmx.rabbitmqtest.domain.dto.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Slf4j
public class FaoutListener1 {
	@RabbitListener(queues = "zmx-faoutqueue1")
	public void subscribe(Mail mail) throws IOException {
		log.info("订阅者1收到消息：【{}】",mail.toString());
	}
}
