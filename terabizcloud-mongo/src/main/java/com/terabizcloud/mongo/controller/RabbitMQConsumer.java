package com.terabizcloud.mongo.controller;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.terabizcloud.mongo.dto.Notification;
import com.terabizcloud.mongo.model.UserLogs;
import com.terabizcloud.mongo.service.UserLogsService;

@Component
public class RabbitMQConsumer {
	@Autowired
	private UserLogsService userLogsService;
	
	@RabbitListener(queues="${terabiz.rabbitmq.queue}")
	 public void recievedMessage(Notification message) {
		  UserLogs userLogs = new UserLogs(); 
		  userLogs.setName(message.getMsg());
		  userLogs.setNotificationType(message.getNotificationType());
		  userLogs.setCreatedTime(LocalDateTime.now()); 
		  userLogsService.save(userLogs);
			 
	      System.out.println("Recieved Message From RabbitMQ: " + message);
    }
	 
}
