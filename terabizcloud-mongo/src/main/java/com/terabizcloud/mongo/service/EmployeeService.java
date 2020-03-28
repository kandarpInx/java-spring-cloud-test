package com.terabizcloud.mongo.service;

import java.util.List;

import com.terabizcloud.mongo.model.Employee;

public interface EmployeeService {
	Employee save(Employee user);
	List<Employee> findAll();
}
