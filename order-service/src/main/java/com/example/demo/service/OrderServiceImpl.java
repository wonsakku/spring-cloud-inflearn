package com.example.demo.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.demo.dto.OrderDto;
import com.example.demo.jpa.OrderEntity;
import com.example.demo.jpa.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public OrderDto createOrder(OrderDto orderDto) {
		
		orderDto.setOrderId(UUID.randomUUID().toString());
		orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());
		
		OrderEntity orderEntity = modelMapper.map(orderDto, OrderEntity.class);
		
		orderRepository.save(orderEntity);
		
		OrderDto returnValue = modelMapper.map(orderEntity, OrderDto.class);
		
		return returnValue;
	}


	@Override
	public OrderDto getOrderByOrderId(String orderId) {
		OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
		OrderDto orderDto = modelMapper.map(orderEntity, OrderDto.class);
		return orderDto;
	}


	@Override
	public Iterable<OrderEntity> getOrdersByUserId(String userId) {
		return orderRepository.findByUserId(userId);
	}
	
	
}
