package com.example.userservice01.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserDto {

	private String email;
	private String name;
	private String pwd;
	private String userId;
	private Date createdAt;
	
	private String encryptedPwd;
}
