package com.example.userservice02.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/userservice-02")
public class UserController {

	@GetMapping("/welcome")
	public String welcome() {
		return "welcome user-service-02"; 
	}
	
	@GetMapping("/message")
	public String message(@RequestHeader("second-request") String header) {
		log.info(header);
		return "message - RequestHeader.second-request => " + header;  
	}
	
}
