package com.terabizcloud.mongo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terabizcloud.mongo.model.UserLogs;
import com.terabizcloud.mongo.repository.UserLogsRepository;
import com.terabizcloud.mongo.service.UserLogsService;

@Service
public class UserLogsServiceImpl implements UserLogsService{

	@Autowired
	private UserLogsRepository userLogsRepo;

	@Override
	public UserLogs save(UserLogs userLogs) {
		return userLogsRepo.save(userLogs);
	}

	@Override
	public List<UserLogs> findAll() {
		return userLogsRepo.findAll();
	}
	
}
