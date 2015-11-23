package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Encounter;
import com.sanyo.quote.domain.Region;

public interface EncounterService {

	List<Encounter> findAll();
	
	Encounter findById(Integer id);
	
	Encounter save(Encounter encounter);
	
	Page<Encounter> findAllByPage(Pageable pageable);
	
	void delete(Integer id);
	
	void delte(Encounter encounter); 
	List<Encounter> getEncountersByRegion(Region region);
	public List<Encounter> findByRegion(Region region);
	
}
