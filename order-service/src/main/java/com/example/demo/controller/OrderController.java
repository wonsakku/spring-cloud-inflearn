package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrderDto;
import com.example.demo.jpa.OrderEntity;
import com.example.demo.service.OrderService;
import com.example.demo.vo.RequestOrder;
import com.example.demo.vo.ResponseOrder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/order-service")
public class OrderController {

	private final OrderService orderService;
	private final Environment env;
	private final ModelMapper modelMapper;

	@GetMapping("/health_check")
	public String status() {
		return String.format("It' Working in User Service on PORT %s", env.getProperty("local.server.port"));
	}

	
	@PostMapping("/{userId}/orders")
	public ResponseEntity<ResponseOrder> createOrder(@RequestBody RequestOrder orderDetail,
													 @PathVariable("userId") String userId){
		OrderDto orderDto = modelMapper.map(orderDetail, OrderDto.class);
		orderDto.setUserId(userId);
		OrderDto createdOrder = orderService.createOrder(orderDto);
		
		ResponseOrder responseOrder = modelMapper.map(createdOrder, ResponseOrder.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
	}
	
	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<ResponseOrder>> getOrder(@RequestBody RequestOrder orderDetail,
														@PathVariable("userId") String userId){
		
		Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);
		List<ResponseOrder> result = new ArrayList<>();
		
		orderList.forEach(order -> {
			result.add(modelMapper.map(order, ResponseOrder.class));
		});
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	
}






































