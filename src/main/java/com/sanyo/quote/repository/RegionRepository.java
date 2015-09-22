package com.sanyo.quote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sanyo.quote.domain.Region;


public interface RegionRepository extends PagingAndSortingRepository<Region, Integer> {

}
