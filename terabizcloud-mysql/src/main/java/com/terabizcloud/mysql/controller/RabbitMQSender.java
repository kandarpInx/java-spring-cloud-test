package com.terabizcloud.mysql.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.terabizcloud.mysql.dto.Notification;

@Service
public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Value("${terabiz.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${terabiz.rabbitmq.routingkey}")
	private String routingkey;	
	String kafkaTopic = "java_in_use_topic";
	
	public void send(Notification notification) {
		amqpTemplate.convertAndSend(exchange, routingkey, notification);
		System.out.println("Send msg = " + notification);
	}
}