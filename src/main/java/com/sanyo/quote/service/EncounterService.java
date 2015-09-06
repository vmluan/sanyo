package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.TH_Encounter;

public interface EncounterService {

	List<TH_Encounter> findAll();
	
	TH_Encounter findById(Integer id);
	
	TH_Encounter save(TH_Encounter th_encounter);
	
	Page<TH_Encounter> findAllByPage(Pageable pageable);	
	
}
