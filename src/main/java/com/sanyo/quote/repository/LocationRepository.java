package com.sanyo.quote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sanyo.quote.domain.Location;


public interface LocationRepository extends PagingAndSortingRepository<Location, Integer> {

}
