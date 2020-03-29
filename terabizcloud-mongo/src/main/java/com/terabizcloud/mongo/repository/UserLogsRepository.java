package com.terabizcloud.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.terabizcloud.mongo.model.UserLogs;

public interface UserLogsRepository extends MongoRepository<UserLogs,String> {

}
