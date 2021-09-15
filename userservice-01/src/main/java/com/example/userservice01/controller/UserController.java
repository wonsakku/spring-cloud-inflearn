package com.example.userservice01.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice01.dto.UserDto;
import com.example.userservice01.service.UserService;
import com.example.userservice01.vo.Greeting;
import com.example.userservice01.vo.RequestUser;
import com.example.userservice01.vo.ResponseUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/userservice-01")
public class UserController {


	private final Environment env;
	private final Greeting greeting;
	private final UserService userService;
	
	@GetMapping("/health_check")
	public String status() {
		return "It' Working in User Service01";
	}
	
	@GetMapping("/welcome")
	public String welcome() {
//		return env.getProperty("greeting.message"); 
		return greeting.getMessage(); 
	}

	@PostMapping("/users")
	public ResponseEntity<ResponseUser> createUser(@RequestBody @Valid RequestUser user) {
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = mapper.map(user, UserDto.class);
		userService.createUser(userDto);
		
		ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
	}
	
	@GetMapping("/message")
	public String message(@RequestHeader("first-request") String header) {
		log.info(header);
		return "message - RequestHeader.first-request => " + header;  
	}
	
}
