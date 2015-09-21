package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.Category;

public interface CategoryService {

	List<Category> findAll();
	
	Category findById(Integer id);
	
	Category save(Category category);
	
	Page<Category> findAllByPage(Pageable pageable);
	List<Category> findByIds(List<Integer > ids);
	List<Category> findParents();
}
