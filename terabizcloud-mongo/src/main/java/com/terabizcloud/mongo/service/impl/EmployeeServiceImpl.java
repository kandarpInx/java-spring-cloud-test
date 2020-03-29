package com.terabizcloud.mongo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.terabizcloud.mongo.model.Employee;
import com.terabizcloud.mongo.repository.EmployeeRepository;
import com.terabizcloud.mongo.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	 @Autowired
	 private EmployeeRepository employeeRepository;

	@Override
	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

}
