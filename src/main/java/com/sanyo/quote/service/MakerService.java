package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.Maker;

public interface MakerService {

	List<Maker> findAll();
	
	Maker findById(Integer id);
	
	Maker save(Maker maker);
	
	Page<Maker> findAllByPage(Pageable pageable);
	
	Maker findByIdAndFetchProductGroupMakerEagerly(Integer id);
	
	Maker findByName(String name);
	
}
