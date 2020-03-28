package com.terabizcloud.mysql.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.terabizcloud.mysql.dto.UserDTO;
import com.terabizcloud.mysql.model.User;
import com.terabizcloud.mysql.repository.UserMapper;
import com.terabizcloud.mysql.util.TerabizResponse;

@RestController
public class UserRestController {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/mysql/registeruser")
	public ResponseEntity<TerabizResponse> insertData(@RequestBody UserDTO userDTO) {
		
		User userModel = new User();
		userModel.setUsername(userDTO.getUsername());
		userModel.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		
		
		int response = userMapper.insert(userModel);
		if(response == 1) {
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.OK.value(), true,
					"Data inserted Successfully", null));
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.OK.value(), false,
					"Error while inserting data", null));
		}
	}
	
	@GetMapping("/mysql/getuser")
	public ResponseEntity<TerabizResponse> getData(@RequestParam("id") Integer id) {
		
		User userModel = userMapper.selectByPrimaryKey(id);

		Map<String, Object> response = new HashMap<String, Object>();
		
		if(null != userModel ) {
			response.put("response", userModel);
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.OK.value(), true,
					"Data fetched Successfully", response));	
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.OK.value(), false,
					"No data found", null));
		}
	}
	
}
