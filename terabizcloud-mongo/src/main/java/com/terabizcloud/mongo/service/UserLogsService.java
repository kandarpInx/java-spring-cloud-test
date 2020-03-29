package com.terabizcloud.mongo.service;

import java.util.List;

import com.terabizcloud.mongo.model.UserLogs;

public interface UserLogsService {
	UserLogs save(UserLogs userLogs);
	List<UserLogs> findAll();

}
