package com.terabizcloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableConfigServer
public class TerabizcloudAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(TerabizcloudAuthApplication.class, args);
	}

}
