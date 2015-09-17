package com.sanyo.quote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Product;
import com.sanyo.quote.domain.Category;


public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
	@Query("select c from Category c where c.categoryID  IN :ids")
	List<Category> findByIds(@Param("ids") List<Integer> ids);

}
