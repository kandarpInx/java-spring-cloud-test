package com.terabizcloud.mongo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.terabizcloud.mongo.model.Employee;
import com.terabizcloud.mongo.service.EmployeeService;


@RestController 
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	 
	//Save Employee
	@RequestMapping(value = "/mongo/save",method = RequestMethod.POST)
	public Employee save(Employee user){
	    return employeeService.save(user);
	}
	 
	// list of all Employee
    @RequestMapping(value = "/mongo/list",method = RequestMethod.GET)
    public List<Employee> listEmployee() {
        return employeeService.findAll();
    }
}
