package com.terabizcloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TerabizcloudAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(TerabizcloudAuthApplication.class, args);
	}

}
