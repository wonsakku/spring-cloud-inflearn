package com.example.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>{

	private final Environment env;
	
	public static class Config{}
	
	
	
	public AuthorizationHeaderFilter(Environment env) {
		super(Config.class);
		this.env = env;
	}


	@Override
	public GatewayFilter apply(Config config) {
		
		return new GatewayFilter() {
			
			@Override
			public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
				
				ServerHttpRequest request = exchange.getRequest();
				
				if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					return onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);
				}
				
				String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				String jwt = authorizationHeader.replace("Bearer", "");
				
				if(!isJwtValid(jwt)) {
					return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
				}
				
				return chain.filter(exchange);
			}
		};
	}

	
	private boolean isJwtValid(String jwt) {
		
		boolean returnValue = true;
		
		String subject = null;
		
		String tokenSecret = env.getProperty("token.secret");
		
		try {
			subject = Jwts.parser()
					.setSigningKey(tokenSecret)
					.parseClaimsJws(jwt).getBody()
					.getSubject();
		} catch (Exception e) {
			returnValue = false;
		} 
		
		if(subject == null || subject.isEmpty()) {
			returnValue = false;
		}
		
		return returnValue;
	}

	
	private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus statusCode) {
		ServerHttpResponse response =exchange.getResponse();
		response.setStatusCode(statusCode);
		
		log.error(errorMessage);
		return response.setComplete();
	}

}






















