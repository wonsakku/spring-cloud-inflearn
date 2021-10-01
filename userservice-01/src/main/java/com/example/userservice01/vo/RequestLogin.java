package com.example.userservice01.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RequestLogin {

	@Email
	@NotNull(message = "이메일은 필수값입니다.")
	@Size(min = 4, message = "이메일의 길이는 최소 4글자입니다.")
	private String email;
	
	@NotNull(message = "비밀번호는 필수값입니다.")
	@Size(min = 4, message = "비밀번호의 길이는 최소 4글자입니다.")
	private String password;
}


