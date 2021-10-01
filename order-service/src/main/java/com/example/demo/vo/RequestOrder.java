package com.example.demo.vo;

import lombok.Data;

@Data
public class RequestOrder {

	private String productId;
	private Integer qty;
	private Integer unitPrice;
}
