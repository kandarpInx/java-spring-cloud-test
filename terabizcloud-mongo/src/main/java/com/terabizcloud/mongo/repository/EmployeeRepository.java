package com.terabizcloud.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.terabizcloud.mongo.model.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee,String>{

}
