package com.sanyo.quote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Maker;


public interface MakerRepository extends PagingAndSortingRepository<Maker, Integer> {
	@Query("SELECT m FROM Maker m JOIN FETCH m.productGroupMakers WHERE m.id = :id")
	public Maker findByIdAndFetchProductGroupMakerEagerly(@Param("id") Integer id);
	
	public Maker findByName(String name);

}
