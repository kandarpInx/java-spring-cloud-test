package com.terabizcloud.mongo.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {
	
	@RabbitListener(queues="${terabiz.rabbitmq.queue}")
	 public void recievedMessage(Message message) {
		byte[] msg = message.getBody();
		String strMsg = new String(msg);
		
        System.out.println("Recieved Message From RabbitMQ: " + strMsg);
    }
	 
}
