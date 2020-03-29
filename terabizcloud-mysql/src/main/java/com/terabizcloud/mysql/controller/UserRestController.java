package com.terabizcloud.mysql.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.terabizcloud.mysql.dto.Notification;
import com.terabizcloud.mysql.dto.UserDTO;
import com.terabizcloud.mysql.model.User;
import com.terabizcloud.mysql.model.UserExample;
import com.terabizcloud.mysql.model.UserExample.Criteria;
import com.terabizcloud.mysql.repository.UserMapper;
import com.terabizcloud.mysql.util.TerabizResponse;

@RestController
public class UserRestController {
	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/register")
	public ResponseEntity<TerabizResponse> registerUser(@RequestBody UserDTO userDTO) {
		logger.info("Executing registerUser...");
		if(null != getUserByUserName(userDTO.getUsername())) {
			logger.info(messageSource.getMessage("user.already.registered.with.username.x", new String[] { userDTO.getUsername() }, Locale.US));
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.FOUND.value(), false,
					messageSource.getMessage("user.already.registered.with.username.x", new String[] { userDTO.getUsername() }, Locale.US), null));
		}
		
		User userModel = new User();
		userModel.setUsername(userDTO.getUsername());
		userModel.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		
		int response = userMapper.insert(userModel);
		if(response == 1) {
			logger.info("sending rabbitmq notification...");
			Notification notification = new Notification();
			notification.setMsg(userModel.getUsername());
			notification.setNotificationType("Register");
			rabbitMQSender.send(notification);
			
			logger.info("Executed registerUser ["+messageSource.getMessage("user.register.success", null, Locale.US)+"].");
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.OK.value(), true,
					messageSource.getMessage("user.register.success", null, Locale.US), null));
		}
		else {
			logger.info("Executed registerUser ["+messageSource.getMessage("user.register.error", null, Locale.US)+"].");
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), false,
					messageSource.getMessage("user.register.error", null, Locale.US), null));
		}
	}
	
	@GetMapping("/user")
	public ResponseEntity<TerabizResponse> getUserById(@RequestParam("id") Integer id) {
		logger.info("Executing getUserById for userId["+id+"].");
		User userModel = userMapper.selectByPrimaryKey(id);
		
		if(null != userModel ) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("response", userModel);
			
			logger.info("Executed getUserById ["+messageSource.getMessage("user.found", null, Locale.US)+"].");
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.FOUND.value(), true,
					messageSource.getMessage("user.found", null, Locale.US), response));	
		}
		else {
			logger.info("Executed getUserById ["+messageSource.getMessage("user.found", null, Locale.US)+"].");
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.NOT_FOUND.value(), false,
					messageSource.getMessage("user.not.found", null, Locale.US), null));
		}
	}
	
	@GetMapping("/user-by-username")
	public ResponseEntity<TerabizResponse> getUserByUsername(@RequestParam("username") String username) {
		logger.info("Executing getUserByUsername ["+username+"].");
		
		User user = getUserByUserName(username);
		
		if(null != user) {
			UserDTO userDto = new UserDTO();
			userDto.setUsername(user.getUsername());
			userDto.setPassword(user.getPassword());
			
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("response", userDto);
			
			logger.info("Executed getUserByUsername ["+messageSource.getMessage("user.found", null, Locale.US)+"].");
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.FOUND.value(), true,
					messageSource.getMessage("user.found", null, Locale.US), response));	
		}
		else {
			logger.info("Executed getUserByUsername ["+messageSource.getMessage("user.not.found", null, Locale.US)+"].");
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.NOT_FOUND.value(), false,
					messageSource.getMessage("user.not.found", null, Locale.US), null));
		}
	}
	
	private User getUserByUserName(String username) {
		UserExample userExample = new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<User> users = userMapper.selectByExample(userExample);
		
		User user = null;
		if(null != users && users.size() > 0 ) {
			user = users.get(0);
		}
		
		return user;
	}
	
	@GetMapping("/users")
	public ResponseEntity<TerabizResponse> getAllUsers() {
		logger.info("Executing getAllUsers...");
		
		List<User> users = userMapper.selectByExample(null);

		Map<String, Object> response = new HashMap<String, Object>();
		
		if(null != users ) {
			logger.info("Executed getAllUsers [success - "+messageSource.getMessage("users.found", null, Locale.US)+"]");
			response.put("response", users);
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.FOUND.value(), true,
					messageSource.getMessage("users.found", null, Locale.US), response));	
		} else {
			logger.info("Executed getAllUsers ["+messageSource.getMessage("no.user.found", null, Locale.US)+"]");
			return ResponseEntity.status(HttpStatus.OK).body(new TerabizResponse(HttpStatus.NOT_FOUND.value(), false,
					messageSource.getMessage("no.user.found", null, Locale.US), null));
		}
	}
	
}
