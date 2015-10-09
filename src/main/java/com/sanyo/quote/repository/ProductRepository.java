package com.sanyo.quote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Product;


public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
//	List<Product> findAllProduct();
	@Query("select c from Product c where c.productName  = :productName")
	List<Product> findProductByName(@Param("productName") String productName);
	
	@Query("select c from Product c where c.isDeleted = false")
	List<Product> findValidProduct();

	@Query("select c from Product c where c.productCode = :productCode")
	Product findProductByCode(@Param("productCode") String productCode);
}
