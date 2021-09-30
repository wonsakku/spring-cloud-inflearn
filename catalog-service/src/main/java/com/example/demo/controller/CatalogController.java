package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jpa.CatalogEntity;
import com.example.demo.service.CatalogService;
import com.example.demo.vo.ResponseCatalog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/catalog-service")
public class CatalogController {

	private final Environment env;
	private final CatalogService catalogService;
	
	@GetMapping("/health_check")
	public String status() {
		return String.format("It' Working in User Service on PORT %s", env.getProperty("local.server.port"));
	}

	
	@GetMapping("/catalogs")
	public ResponseEntity<List<ResponseCatalog>> getCatalogs(){
		Iterable<CatalogEntity> catalogList = catalogService.getAllCatalogs();
		
		List<ResponseCatalog> result = new ArrayList<>();
		
		ModelMapper modelMapper = new ModelMapper();
		
		catalogList.forEach(v -> {
			result.add(modelMapper.map(v, ResponseCatalog.class));
		});
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	

}

















