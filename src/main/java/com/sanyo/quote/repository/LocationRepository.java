package com.sanyo.quote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Location;


public interface LocationRepository extends PagingAndSortingRepository<Location, Integer> {
	@Query("SELECT l FROM Location l JOIN FETCH l.regions WHERE l.locationId = :id")
	Location findByIdAndFetchRegionsEagerly(@Param("id") Integer id);
}
