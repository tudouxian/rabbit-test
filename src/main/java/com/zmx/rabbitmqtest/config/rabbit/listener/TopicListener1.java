package com.zmx.rabbitmqtest.config.rabbit.listener;

import com.zmx.rabbitmqtest.domain.dto.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Slf4j
public class TopicListener1 {
	@RabbitListener(queues = "zmx-topicqueue1")
	public void displayTopic(Mail mail) throws IOException {
		log.info("从topicqueue1取出消息：【{}】",mail.toString());
		}
}
