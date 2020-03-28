package com.terabizcloud.mongo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.terabizcloud.mongo.model.Employee;
import com.terabizcloud.mongo.service.EmployeeService;
import com.terabizcloud.mongo.util.TerabizResponse;


@RestController 
public class EmployeeController {
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private EmployeeService employeeService;
	 
	//Save Employee
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public ResponseEntity<TerabizResponse> save(Employee emp){
		
	    Employee employee = employeeService.save(emp);
	    if(null != employee) {
	    	return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.FOUND.value(), true,
				messageSource.getMessage("employee.add.success", null, Locale.US), null));
	    } else {
	    	return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.FOUND.value(), true,
				messageSource.getMessage("employee.add.error", null, Locale.US), null));
	    }
	}
	 
	// list of all Employee
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseEntity<TerabizResponse> listEmployee() {
        List<Employee> list = employeeService.findAll();
        
        if(null != list && !list.isEmpty()) {
        	Map<String, Object> response = new HashMap<String, Object>();
			response.put("response", list);
			
        	return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.FOUND.value(), true,
					messageSource.getMessage("employees.found", null, Locale.US), response));	
        } else {
        	return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.FOUND.value(), true,
					messageSource.getMessage("no.employee.found", null, Locale.US), null));	
        }
    }
}
