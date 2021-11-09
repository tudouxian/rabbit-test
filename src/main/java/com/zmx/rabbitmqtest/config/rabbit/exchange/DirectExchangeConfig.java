package com.zmx.rabbitmqtest.config.rabbit.exchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//direct直连模式的交换机配置,包括一个direct交换机，两个队列，三根网线binding
@Configuration
public class DirectExchangeConfig {
	@Bean
 	public DirectExchange directExchange(){
		DirectExchange directExchange=new DirectExchange("zmx-direct");
 		return directExchange;
 	}
	
	@Bean
    public Queue directQueue1() {
       Queue queue=new Queue("zmx-directqueue1");
       return queue;
    }
 	
 	@Bean
    public Queue directQueue2() {
       Queue queue=new Queue("zmx-directqueue2");
       return queue;
    }

	@Bean
	public Queue directQueue3() {
		Queue queue=new Queue("zmx-directqueue3");
		return queue;
	}
	
 	//3个binding将交换机和相应队列连起来
 	@Bean
 	public Binding bindingorange(){
 		Binding binding= BindingBuilder.bind(directQueue1())
				.to(directExchange())
				.with("orange");
 		return binding;
 	}

	@Bean
	public Binding bindingorange2(){
		Binding binding= BindingBuilder.bind(directQueue2())
				.to(directExchange())
				.with("orange");
		return binding;
	}

	@Bean
	public Binding bindingorange3(){
		Binding binding= BindingBuilder.bind(directQueue3())
				.to(directExchange())
				.with("orange");
		return binding;
	}
 	
 	@Bean
 	public Binding bindingblack(){
 		Binding binding= BindingBuilder.bind(directQueue2())
				.to(directExchange())
				.with("black");
 		return binding;
 	}
 	
 	@Bean
 	public Binding bindinggreen(){
 		Binding binding= BindingBuilder.bind(directQueue2())
				.to(directExchange())
				.with("green");
 		return binding;
 	}
 	
 	
 	
}
