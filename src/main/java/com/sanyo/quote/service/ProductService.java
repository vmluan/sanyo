package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.Product;

public interface ProductService {

	List<Product> findAll();
	
	Product findById(Integer id);
	
	Product save(Product TH_Product);
	
	Page<Product> findAllByPage(Pageable pageable);
	
	Product findByName(String productName);

	Product findByCode (String productCode);
	
	void delete(Integer id);
	void delte(Product product);
	
	void deleteProduct(Product product);
	
	
}
