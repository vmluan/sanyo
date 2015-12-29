package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.LocationOrderHist;

public interface LocationOrderHistService {

	List<LocationOrderHist> findAll();
	
	LocationOrderHist findById(Integer id);
	
	LocationOrderHist save(LocationOrderHist LocationOrderHist);
	
	Page<LocationOrderHist> findAllByPage(Pageable pageable);
	
	void delete(Integer id);
	
	void delte(LocationOrderHist LocationOrderHist); 
	
}
