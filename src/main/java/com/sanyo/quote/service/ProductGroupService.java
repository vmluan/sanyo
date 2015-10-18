package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.ProductGroup;

public interface ProductGroupService {

	List<ProductGroup> findAll();
	
	ProductGroup findById(Integer id);
	
	ProductGroup save(ProductGroup productGroup);
	
	Page<ProductGroup> findAllByPage(Pageable pageable);
	
	ProductGroup findByIdAndFetchProductGroupMakerEagerly(Integer id);
	
	ProductGroup findByIdAndFetchProductsEagerly(Integer id);
}
