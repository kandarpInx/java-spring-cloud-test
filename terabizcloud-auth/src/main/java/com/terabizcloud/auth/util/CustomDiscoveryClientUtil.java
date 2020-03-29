package com.terabizcloud.auth.util;

import java.net.URI;
import java.util.Optional;

import javax.naming.ServiceUnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.terabizcloud.auth.model.User;

@Component
public class CustomDiscoveryClientUtil {
	@Autowired
	private static DiscoveryClient discoveryClient;
	
	@Autowired
	private static RestTemplate restTemplate;
	
	private static Optional<URI> serviceUrl(String instanceId) {
        return discoveryClient.getInstances(instanceId)
          .stream()
          .findFirst() 
          .map(si -> si.getUri());
    }
	
	public static User loadUserByUsername(String username) throws ServiceUnavailableException {
		URI uri = serviceUrl("terabizcloud-mysql").map(s -> s.resolve("/user-by-username?username="+username))
			      .orElseThrow(ServiceUnavailableException::new);
		return restTemplate.getForEntity(uri, User.class).getBody();
	}
}
