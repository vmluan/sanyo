package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.ProductGroupMaker;

public interface ProductGroupMakerService {

	List<ProductGroupMaker> findAll();
	
	ProductGroupMaker findById(Integer id);
	
	ProductGroupMaker save(ProductGroupMaker productGroupMaker);
	
	Page<ProductGroupMaker> findAllByPage(Pageable pageable);
}
