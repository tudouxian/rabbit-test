package com.zmx.rabbitmqtest.domain.dto;

public class TopicMail extends Mail{

	//邮件-路由key
	String routingkey;

	public String getRoutingkey() {
		return routingkey;
	}

	public void setRoutingkey(String routingkey) {
		this.routingkey = routingkey;
	}

	@Override
	public String toString() {
		return "TopicMail [routingkey=" + routingkey + "]";
	}
	
}
