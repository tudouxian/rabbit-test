package com.zmx.rabbitmqtest.config.rabbit.listener;

import com.zmx.rabbitmqtest.domain.dto.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
*   @Author: 土豆仙
*   @Date: 2021/11/6 16:22
*   @Description: 直连监听关闭后，再启动也能继续消费队列中的消息
*/
@Component
@Slf4j
public class DirectListener2 {
	@RabbitListener(queues = "zmx-directqueue2")
	public void displayMail(Mail mail) throws Exception {
		log.info("directqueue2队列监听器2号收到消息:【{}】",mail.toString());
	}

	//自动ACK：消息一旦被接收，消费者自动发送ACK
	//手动ACK：消息接收后，不会发送ACK，需要手动调用
}
