package com.example.userservice01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/userservice-01")
public class UserController {

	@GetMapping("/welcome")
	public String welcome() {
		return "welcome user-service-01"; 
	}
	
	@GetMapping("/message")
	public String message(@RequestHeader("first-request") String header) {
		log.info(header);
		return "message - RequestHeader.first-request => " + header;  
	}
	
}
