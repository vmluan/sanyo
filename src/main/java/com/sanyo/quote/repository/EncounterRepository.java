package com.sanyo.quote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sanyo.quote.domain.Encounter;


public interface EncounterRepository extends PagingAndSortingRepository<Encounter, Integer> {
	

}
