package com.terabizcloud.mongo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.terabizcloud.mongo.model.UserLogs;
import com.terabizcloud.mongo.service.UserLogsService;
import com.terabizcloud.mongo.util.TerabizResponse;

@RestController
public class UserLogsController {
	
	@Autowired
	private UserLogsService userLogsService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/userslogs")
	public ResponseEntity<TerabizResponse> listOfUserLogs(){
		 List<UserLogs> list = userLogsService.findAll();
		 if(null != list && !list.isEmpty()) {
        	Map<String, Object> response = new HashMap<String, Object>();
			response.put("response", list);
			
        	return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.FOUND.value(), true,
					messageSource.getMessage("userLogs.found", null, Locale.US), response));	
        } else {
        	return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.FOUND.value(), true,
					messageSource.getMessage("no.userlogs.found", null, Locale.US), null));	
        }
	}
}
