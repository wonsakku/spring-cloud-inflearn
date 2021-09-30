package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.jpa.CatalogEntity;
import com.example.demo.jpa.CatalogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CatalogServiceImpl implements CatalogService{

	private final CatalogRepository catalogRepository; 
	
	
	@Override
	public Iterable<CatalogEntity> getAllCatalogs() {
		return catalogRepository.findAll();
	}
}
















