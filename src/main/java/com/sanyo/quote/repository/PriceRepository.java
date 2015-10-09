package com.sanyo.quote.repository;

import com.sanyo.quote.domain.Price;
import com.sanyo.quote.domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by User on 10/9/2015.
 */
public interface PriceRepository extends PagingAndSortingRepository<Price, Integer> {

    @Query("select c from Price c where c.product  = :productID")
    List<Price> findPriceByProductID(@Param("productID") Integer productID);
}
