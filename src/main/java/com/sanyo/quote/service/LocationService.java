package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.Location;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.Region;

public interface LocationService {

	List<Location> findAll();
	
	Location findById(Integer id);
	
	Location save(Location Location);
	
	Page<Location> findAllByPage(Pageable pageable);
	
	Location findByIdAndFetchRegionsEagerly(Integer id);
	
	List<Region> findRegions(Integer id);
	
	void delete(Integer id);
	void delete(Location location);
	List<Location> findByProject(Project project);
	
}
