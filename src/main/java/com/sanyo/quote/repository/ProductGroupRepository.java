package com.sanyo.quote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Maker;
import com.sanyo.quote.domain.ProductGroup;


public interface ProductGroupRepository extends PagingAndSortingRepository<ProductGroup, Integer> {
	@Query("SELECT pg FROM ProductGroup pg JOIN FETCH pg.productGroupMakers WHERE pg.groupId = :id")
	public ProductGroup findByIdAndFetchProductGroupMakerEagerly(@Param("id") Integer id);
	
	@Query("SELECT pg FROM ProductGroup pg JOIN FETCH pg.products WHERE pg.groupId = :id")
	public ProductGroup findByIdAndFetchProductsEagerly(@Param("id") Integer id);
	
	public ProductGroup findByGroupName(String groupName);
	public ProductGroup findByGroupCode(String groupCode);

}
