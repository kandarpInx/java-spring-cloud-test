package com.terabizcloud.auth.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscoveryClientController {

	@Autowired
	private DiscoveryClient discoveryClient;
	
	public Optional<URI> serviceUrl(String instanceId) {
        return discoveryClient.getInstances(instanceId)
          .stream()
          .findFirst() 
          .map(si -> si.getUri());
    }
}
