package com.terabizcloud.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan
@EnableMongoRepositories
@EnableDiscoveryClient
public class TerabizcloudMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TerabizcloudMongoApplication.class, args);
	}

}
