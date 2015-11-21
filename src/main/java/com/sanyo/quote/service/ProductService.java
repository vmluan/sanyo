package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.Category;
import com.sanyo.quote.domain.LabourPrice;
import com.sanyo.quote.domain.Maker;
import com.sanyo.quote.domain.Product;
import com.sanyo.quote.domain.ProductGroup;

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
	public List<LabourPrice> findLabourPrices(Integer id);
	public List<Category> findCategories(Integer id);
	public List<Product> findByProductGroup(ProductGroup productGroup);
	public List<Product> findByProductGroupAndMaker(ProductGroup productGroup, Maker maker);
	
}
