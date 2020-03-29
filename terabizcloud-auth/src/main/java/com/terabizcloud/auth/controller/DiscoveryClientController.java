package com.terabizcloud.auth.controller;

import javax.naming.ServiceUnavailableException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terabizcloud.auth.model.User;
import com.terabizcloud.auth.util.CustomDiscoveryClientUtil;

@RestController
public class DiscoveryClientController {
	@GetMapping("/test")
	public User test() {
		User user = null;
		try {
			user = CustomDiscoveryClientUtil.loadUserByUsername("admin");
		} catch (ServiceUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
}
