package com.zmx.rabbitmqtest.service.impl;


import com.zmx.rabbitmqtest.domain.dto.Mail;
import com.zmx.rabbitmqtest.service.RabbitProducerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
*   @Author: 土豆仙
*   @Date: 2021/11/6 14:10
*   @Description: 生产-消费模式
*/
@Transactional
@Service("producer")
public class RabbitProducerServiceImpl implements RabbitProducerService {
	@Autowired
    RabbitTemplate rabbitTemplate;
	public void sendMail(String exchange, String routingkey, Mail mail) {
		//rabbitTemplate.setQueue(queue);
		rabbitTemplate.convertAndSend(exchange,routingkey,mail);
	}

}
