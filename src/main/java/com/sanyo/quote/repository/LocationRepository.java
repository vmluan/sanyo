package com.sanyo.quote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Location;
import com.sanyo.quote.domain.Region;


public interface LocationRepository extends PagingAndSortingRepository<Location, Integer> {
	@Query("SELECT l FROM Location l JOIN FETCH l.regions WHERE l.locationId = :id")
	Location findByIdAndFetchRegionsEagerly(@Param("id") Integer id);
	
	@Query("SELECT distinct l.regions FROM Location l JOIN l.regions WHERE l.locationId = :id")
	List<Region> findRegions(@Param("id") Integer id);
}
