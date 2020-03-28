package com.terabizcloud.auth.service;

import java.net.URI;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.terabizcloud.auth.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
//	@Autowired
//	private DiscoveryClient discoveryClient;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Optional<URI> uri = discoveryClient.getInstances("terabizcloud-mysql").stream().findFirst().map(si -> si.getUri());
		
		Optional<com.terabizcloud.auth.model.User> userDto = userRepository.findByUsername(username);
		if(!userDto.isPresent()) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(userDto.get().getUsername(), userDto.get().getPassword(), Collections.emptyList());
	}

}
