package com.example.userservice01.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice01.vo.Greeting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/userservice-01")
public class UserController {


	private final Environment env;
	private final Greeting greeting;
	
	@GetMapping("/health_check")
	public String status() {
		return "It' Working in User Service01";
	}
	
	@GetMapping("/welcome")
	public String welcome() {
//		return env.getProperty("greeting.message"); 
		return greeting.getMessage(); 
	}
	
	@GetMapping("/message")
	public String message(@RequestHeader("first-request") String header) {
		log.info(header);
		return "message - RequestHeader.first-request => " + header;  
	}
	
}
