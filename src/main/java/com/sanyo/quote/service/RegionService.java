package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.Region;

public interface RegionService {

	List<Region> findAll();
	
	Region findById(Integer id);
	
	Region save(Region Region);
	
	Page<Region> findAllByPage(Pageable pageable);
	
	Region findByIdAndFetchUsersEagerly(Integer id);
	Region findByIdAndFetchUserRegionRolesEagerly(Integer id);
}
