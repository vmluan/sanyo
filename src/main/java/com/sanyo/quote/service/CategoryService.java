package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.TH_Category;

public interface CategoryService {

	List<TH_Category> findAll();
	
	TH_Category findById(Integer id);
	
	TH_Category save(TH_Category category);
	
	Page<TH_Category> findAllByPage(Pageable pageable);
	List<TH_Category> findByIds(List<Integer > ids);
	
}
