package com.terabizcloud.mysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TerabizcloudMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(TerabizcloudMysqlApplication.class, args);
	}

}
