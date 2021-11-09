package com.zmx.rabbitmqtest.config.rabbit.listener;


import com.zmx.rabbitmqtest.domain.dto.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QueueListener2 {
	
	@RabbitListener(queues = "zmxqueue")
	public void displayMail(Mail mail) throws Exception {
		log.info("队列监听器2号收到消息：【{}】",mail.toString());
	}
}
