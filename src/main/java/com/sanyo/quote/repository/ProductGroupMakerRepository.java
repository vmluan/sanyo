package com.sanyo.quote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sanyo.quote.domain.ProductGroupMaker;


public interface ProductGroupMakerRepository extends PagingAndSortingRepository<ProductGroupMaker, Integer> {
	

}
