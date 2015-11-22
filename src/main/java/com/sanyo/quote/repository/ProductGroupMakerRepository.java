package com.sanyo.quote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Maker;
import com.sanyo.quote.domain.ProductGroup;
import com.sanyo.quote.domain.ProductGroupMaker;


public interface ProductGroupMakerRepository extends PagingAndSortingRepository<ProductGroupMaker, Integer> {
	@Query("SELECT distinct  p.maker FROM ProductGroupMaker p JOIN p.maker WHERE p.productGroup = :productGroup")
	public List<Maker> findMakersOfProductGroup(@Param("productGroup") ProductGroup productGroup);
	
	public List<ProductGroupMaker> findByProductGroupAndMaker(ProductGroup productGroup, Maker maker);
}
