package com.terabizcloud.mysql.controller;

import java.util.HashMap;
import java.util.List;
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

import com.google.gson.JsonObject;
import com.terabizcloud.mysql.dto.Notification;
import com.terabizcloud.mysql.dto.UserDTO;
import com.terabizcloud.mysql.model.User;
import com.terabizcloud.mysql.model.UserExample;
import com.terabizcloud.mysql.model.UserExample.Criteria;
import com.terabizcloud.mysql.repository.UserMapper;
import com.terabizcloud.mysql.util.TerabizResponse;

@RestController
public class UserRestController {

	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/registeruser")
	public ResponseEntity<TerabizResponse> insertData(@RequestBody UserDTO userDTO) {
		
		User userModel = new User();
		userModel.setUsername(userDTO.getUsername());
		userModel.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		
		
		int response = userMapper.insert(userModel);
		if(response == 1) {
			Notification notification = new Notification();
			notification.setMsg(userModel.getUsername());
			notification.setNotificationType("Register");
			JsonObject msg = new JsonObject();
			msg.addProperty("msg", userModel.getUsername());
			msg.addProperty("notificationType","Register");
			
			rabbitMQSender.send(msg);
			
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.OK.value(), true,
					"Data inserted Successfully", null));
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.OK.value(), false,
					"Error while inserting data", null));
		}
	}
	
	@GetMapping("/getuser")
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
	
	@GetMapping("/getbyusername")
	public ResponseEntity<TerabizResponse> getData(@RequestParam("username") String username) {
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<User> users = userMapper.selectByExample(userExample);

		Map<String, Object> response = new HashMap<String, Object>();
		
		if(null != users && users.size() > 0 ) {
			User user = users.get(0);
			UserDTO userDto = new UserDTO();
			userDto.setUsername(user.getUsername());
			userDto.setPassword(user.getPassword());
			
			response.put("response", userDto);
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.OK.value(), true,
					"Data fetched Successfully", response));	
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.OK.value(), false,
					"No data found", null));
		}
	}
	
	@GetMapping("/getusers")
	public ResponseEntity<TerabizResponse> getAllData() {
		
		List<User> users = userMapper.selectByExample(null);

		Map<String, Object> response = new HashMap<String, Object>();
		
		if(null != users ) {
			response.put("response", users);
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.OK.value(), true,
					"Data fetched Successfully", response));	
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.OK.value(), false,
					"No data found", null));
		}
	}
	
}
