package com.terabizcloud.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
	@GetMapping("/my-health-check")
	public ResponseEntity<String> myCustomCheck() {
	    String message = "Testing my healh check function";
	    return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
	}
}
