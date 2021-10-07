package com.example.userservice01.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.userservice01.dto.UserDto;
import com.example.userservice01.service.UserService;
import com.example.userservice01.vo.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private UserService userService;
	private Environment env;
	
	
	public AuthenticationFilter(AuthenticationManager authenticationManager, 
								UserService userService, 
								Environment env) {

		super.setAuthenticationManager(authenticationManager);
		this.userService = userService;
		this.env = env;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
			
			return getAuthenticationManager()
					.authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()))
					;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
//		super.successfulAuthentication(request, response, chain, authResult);
//		log.debug(((User)authResult.getPrincipal()).getUsername());
		
		String email = ((User)authResult.getPrincipal()).getUsername();
		UserDto userDetails = userService.getUserDetailsByEmail(email);
		
		String tokenSecret = env.getProperty("token.secret");
		
		String token = Jwts.builder()
				.setSubject(userDetails.getUserId())
				.setExpiration(
						new Date(
								System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))
								)
						)
				.signWith(SignatureAlgorithm.HS512, tokenSecret)
				.compact();
		
		response.addHeader("token", token);
		response.addHeader("userId", userDetails.getUserId());
	}

	
}


















