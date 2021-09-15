package com.example.userservice01.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RequestUser {


	@NotNull(message = "이메일은 필수값입니다.")
	@Size(min = 2, message = "최소 2글자 이상 입력해야 합니다.")
	@Email(message = "이메일 형식에 맞지 않습니다.")
	private String email;
	
	@NotNull(message = "이름은 필수값입니다.")
	@Size(min = 2, message = "최소 2글자 이상 입력해야 합니다.")
	private String name;
	
	@NotNull(message = "비밀번호는 필수값입니다.")
	@Size(min = 2, message = "최소 2글자 이상 입력해야 합니다.")
	private String pwd;
}

