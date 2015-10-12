package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.Location;

public interface LocationService {

	List<Location> findAll();
	
	Location findById(Integer id);
	
	Location save(Location Location);
	
	Page<Location> findAllByPage(Pageable pageable);	
	
}
