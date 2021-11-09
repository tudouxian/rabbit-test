package com.zmx.rabbitmqtest.controller;


import com.zmx.rabbitmqtest.domain.dto.Mail;
import com.zmx.rabbitmqtest.domain.dto.TopicMail;
import com.zmx.rabbitmqtest.service.RabbitProducerService;
import com.zmx.rabbitmqtest.service.RabbitPublisherService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.zmx.rabbitmqtest.config.FlowRabbitConfig.EXCHANGE;

/**
 * @Author: 土豆仙
 * @Date: 2021/11/6 9:56
 * @Description: 消息队列测试类
 */
@RestController
@RequestMapping("/mq")
public class RabbitMqController {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Autowired
    RabbitProducerService producer;

    @Autowired
    RabbitPublisherService publisher;


    //测试
    @GetMapping("/routerKey")
    public void queryUsersByGroupKey(@RequestParam("routerKey") String routerKey) {

        rabbitTemplate.convertAndSend(EXCHANGE, routerKey, "你好啊！！！");
    }

    //使用直连队列-默认交换机 生产-消费模式
    @RequestMapping(value = "/produce", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void produce(@ModelAttribute("mail") Mail mail,
                        @RequestParam("routerKey") String routerKey) throws Exception {

        rabbitTemplate.convertAndSend(mail.toString());
       // producer.sendMail("zmx-direct", routerKey, mail);
    }


    //使用direct交换机
    @RequestMapping(value = "/directDefault", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void direct(@ModelAttribute("mail") Mail mail,
                        @RequestParam("routerKey") String routerKey) throws Exception {
        producer.sendMail("zmx-direct", routerKey, mail);
    }


    //使用direct交换机
    @RequestMapping(value = "/directExchange", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void direct(@ModelAttribute("mail") TopicMail mail) {
        Mail m = new Mail(mail.getMailId(), mail.getCountry(), mail.getWeight());
        publisher.senddirectMail(m, mail.getRoutingkey());
    }


    //使用faout交换机
    @RequestMapping(value = "/faoutExchange", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void faoutExchange(@ModelAttribute("mail") Mail mail) throws Exception {
        publisher.publishMail(mail);
    }

    //使用topic交换机
    @RequestMapping(value = "/topicExchange", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void topicExchange(@ModelAttribute("mail") TopicMail mail) {
        Mail m = new Mail(mail.getMailId(), mail.getCountry(), mail.getWeight());
        publisher.sendtopicMail(m, mail.getRoutingkey());
    }

    //使用headers交换机
    @RequestMapping(value = "/headersExchange", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void headers(@ModelAttribute("mail") TopicMail mail) {
        Mail m = new Mail(mail.getMailId(), mail.getCountry(), mail.getWeight());
        publisher.sendHeadersMail(m, null);
    }


    //使用测试延时队列和死信队列
    @RequestMapping(value = "/delayExchange", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void delay(@ModelAttribute("mail") TopicMail mail) {
        Mail m = new Mail(mail.getMailId(), mail.getCountry(), mail.getWeight());

        rabbitTemplate.convertAndSend("zmx-delay-work-exchange",
                "q5s",
                m,
                message -> {
                    //配置消息的过期时间
                    message.getMessageProperties().setDelay(6000);
                    return message;
                }
              );
    }


    //测试延时队列插件


}
