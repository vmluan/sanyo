package com.sanyo.quote.repository;

import com.sanyo.quote.domain.ProductGroupRate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Chuong on 11/30/2015.
 */
public interface ProductGroupRateRepository extends PagingAndSortingRepository<ProductGroupRate, Integer> {

    @Query("SELECT p FROM ProductGroupRate p WHERE p.project = :projectId")
    public List<ProductGroupRate> findByProjectId(@Param("projectId")Integer projectId);
}

