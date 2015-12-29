package com.sanyo.quote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sanyo.quote.domain.LocationOrderHist;


public interface LocationOrderHistRepository extends PagingAndSortingRepository<LocationOrderHist, Integer> {

}
