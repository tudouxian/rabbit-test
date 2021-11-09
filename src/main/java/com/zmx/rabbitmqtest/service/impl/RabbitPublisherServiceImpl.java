package com.zmx.rabbitmqtest.service.impl;


import com.zmx.rabbitmqtest.domain.dto.Mail;
import com.zmx.rabbitmqtest.service.RabbitPublisherService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Correlation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
*   @Author: 土豆仙
*   @Date: 2021/11/6 14:10
*   @Description: 发布订阅模式
*/
@Service("publisher")
public class RabbitPublisherServiceImpl implements RabbitPublisherService {
	@Autowired
    RabbitTemplate rabbitTemplate;

	public void publishMail(Mail mail) {
		rabbitTemplate.convertAndSend("zmx-fanout", null, mail);
	}

	public void senddirectMail(Mail mail, String routingkey) {
		rabbitTemplate.convertAndSend("zmx-direct", routingkey, mail);
	}

	public void sendtopicMail(Mail mail, String routingkey) {
		rabbitTemplate.convertAndSend("zmx-topic", routingkey, mail);
	}

	public void sendHeadersMail(Mail mail, Map<String,Object> headers) {
		MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {

				message.getMessageProperties().setHeader("userName","zmx1");
				return message;
			}

		};
		rabbitTemplate.convertAndSend("zmx-headers","", mail,messagePostProcessor);
	}
	
	
}
