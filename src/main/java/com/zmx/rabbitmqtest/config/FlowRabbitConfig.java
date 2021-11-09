package com.zmx.rabbitmqtest.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
public class FlowRabbitConfig {

    public static final String EXCHANGE = "zmx";
    public static final String QUEUE = "zmx-rabbitmq-queue";
    public static final String ROUTINGKEY = "zmx-history";


    //异步任务线程池
    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    /**
     * 应用启动时创建交换机和队列
     *
     * @param taskExecutor
     * @param amqpAdmin
     * @return
     */
    @Bean
    public CommandLineRunner schedulingRunner(@Qualifier("taskExecutor") TaskExecutor taskExecutor,
                                              AmqpAdmin amqpAdmin) {
        return args -> taskExecutor.execute(() -> {

            //创建交换机
            TopicExchange exchange = new TopicExchange(EXCHANGE);
            //声明交换机
            amqpAdmin.declareExchange(exchange);
            //队列持久化
            Queue queue = new Queue(QUEUE, true);
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY));
        });
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }


    //声明交换机
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    //声明队列
    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }

    //将队列绑定到交换机
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTINGKEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory());
        return rabbitTemplate;
    }


    //监听器配置
    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE);
        container.setMessageListener(listenerAdapter);
        //设置消费者数量，flowable异步消息处理，多个消息是一组，一组消息乱序会出错，所以不支持多个消费者消费
        container.setConcurrentConsumers(1);
        container.start();
        return container;
    }


    /**
     * 配置rabbitmq连接
     *
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.rabbitmq")
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory();
    }


    //监听器适配器-主要处理接收消息
    @Bean
    public MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    //消息接收
    @Bean
    public Receiver receiver() {
        Receiver receiver = new Receiver();
        return receiver;
    }


}
