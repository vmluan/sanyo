package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.Encounter;

public interface EncounterService {

	List<Encounter> findAll();
	
	Encounter findById(Integer id);
	
	Encounter save(Encounter encounter);
	
	Page<Encounter> findAllByPage(Pageable pageable);
	
	void delete(Integer id);
	
	void delte(Encounter encounter);
	
}
