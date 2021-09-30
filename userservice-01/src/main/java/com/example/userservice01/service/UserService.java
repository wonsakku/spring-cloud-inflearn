package com.example.userservice01.service;

import com.example.userservice01.dto.UserDto;
import com.example.userservice01.jpa.UserEntity;

public interface UserService {
	void createUser(UserDto userDto);
	UserDto getUserByUserId(String userId);
	Iterable<UserEntity> getUserByAll();
}
