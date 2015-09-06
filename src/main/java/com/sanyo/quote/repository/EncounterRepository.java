package com.sanyo.quote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sanyo.quote.domain.TH_Encounter;


public interface EncounterRepository extends PagingAndSortingRepository<TH_Encounter, Integer> {
	

}
