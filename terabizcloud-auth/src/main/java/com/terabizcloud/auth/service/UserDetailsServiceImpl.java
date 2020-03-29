package com.terabizcloud.auth.service;

import java.util.Collections;

import javax.naming.ServiceUnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.terabizcloud.auth.util.CustomDiscoveryClientUtil;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private CustomDiscoveryClientUtil customDiscoveryClientUtil;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.terabizcloud.auth.model.User user = null;
		try {
			user = customDiscoveryClientUtil.loadUserByUsername(username);
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
		
		if(null == user) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
	}

}
