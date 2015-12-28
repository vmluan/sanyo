package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.EncounterOrderHist;

public interface EncounterOrderHistService {

	List<EncounterOrderHist> findAll();
	
	EncounterOrderHist findById(Integer id);
	
	EncounterOrderHist save(EncounterOrderHist EncounterOrderHist);
	
	Page<EncounterOrderHist> findAllByPage(Pageable pageable);
	
	void delete(Integer id);
	
	void delte(EncounterOrderHist EncounterOrderHist); 
	
}
