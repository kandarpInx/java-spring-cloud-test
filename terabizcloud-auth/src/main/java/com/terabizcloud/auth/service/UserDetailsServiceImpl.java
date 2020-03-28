package com.terabizcloud.auth.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<com.terabizcloud.auth.model.User> userDto = userRepository.findByUsername(username);
		if(!userDto.isPresent()) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(userDto.get().getUsername(), userDto.get().getPassword(), Collections.emptyList());
	}

}
