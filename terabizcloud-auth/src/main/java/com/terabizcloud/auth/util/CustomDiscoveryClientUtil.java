package com.terabizcloud.auth.util;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.naming.ServiceUnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.terabizcloud.auth.model.User;

@Component
public class CustomDiscoveryClientUtil {
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private Optional<URI> serviceUrl(String instanceId) {
		List<ServiceInstance> instances = discoveryClient.getInstances(instanceId);
		if(null == instances || instances.isEmpty()) {
			return Optional.empty();
		}
		
        return discoveryClient.getInstances(instanceId)
          .stream()
          .findFirst() 
          .map(si -> si.getUri());
    }
	
	public User loadUserByUsername(String username) throws ServiceUnavailableException {
		URI uri = serviceUrl("terabizcloud-mysql").map(s -> s.resolve("/user-by-username?username="+username))
			      .orElseThrow(ServiceUnavailableException::new);
		Map response = restTemplate.getForObject(uri, Map.class);
		User user = null;
		if("true".equals(response.get("success").toString()) &&  Integer.parseInt(response.get("status").toString()) == HttpStatus.FOUND.value()) {
			Map userMap = ((Map)((Map)response.get("data")).get("response"));
			user = new User();
			user.setUsername(userMap.get("username").toString());
			user.setPassword(userMap.get("password").toString());
		}
		return user;
	}
}
