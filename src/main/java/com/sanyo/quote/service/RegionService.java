package com.sanyo.quote.service;

import com.sanyo.quote.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RegionService {

	List<Region> findAll();
	
	Region findById(Integer id);
	
	Region save(Region Region);
	
	Page<Region> findAllByPage(Pageable pageable);
	
//	Region findByIdAndFetchUsersEagerly(Integer id);
	Region findByIdAndFetchUserRegionRolesEagerly(Integer id);
	
	Region findByIdAndFetchEncountersEagerly(Integer id);
	
	List<Encounter> getEncounters(Integer id);
	List<UserRegionRole> getUserRegionRoles( Integer id);

	void delete(Integer id);
	void delete(Region region);
	public List<Region> findByLocation(Location location);
}
