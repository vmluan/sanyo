package com.sanyo.quote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Category;
import com.sanyo.quote.domain.LabourPrice;
import com.sanyo.quote.domain.Product;


public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
//	List<Product> findAllProduct();
	@Query("select c from Product c where c.productName  = :productName")
	List<Product> findProductByName(@Param("productName") String productName);
	
	@Query("select c from Product c where c.isDeleted = false")
	List<Product> findValidProduct();

	@Query("select c from Product c where c.productCode = :productCode")
	Product findProductByCode(@Param("productCode") String productCode);
	
	@Query("SELECT distinct  p.labourPrices FROM Product p JOIN p.labourPrices WHERE p.productID = :id")
	public List<LabourPrice> findLabourPrices(@Param("id") Integer id);
	
	@Query("SELECT distinct  p.categories FROM Product p JOIN p.categories WHERE p.productID = :id")
	public List<Category> findCategories(@Param("id") Integer id);
}
