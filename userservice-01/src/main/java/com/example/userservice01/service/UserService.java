package com.example.userservice01.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.userservice01.dto.UserDto;
import com.example.userservice01.jpa.UserEntity;

public interface UserService extends UserDetailsService{
	
	void createUser(UserDto userDto);
	UserDto getUserByUserId(String userId);
	Iterable<UserEntity> getUserByAll();
	UserDto getUserDetailsByEmail(String email);
}
