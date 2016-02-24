package com.sanyo.quote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.ProductGroup;
import com.sanyo.quote.domain.ProductGroupRate;
import com.sanyo.quote.domain.Project;

/**
 * Created by Chuong on 11/30/2015.
 */
public interface ProductGroupRateRepository extends PagingAndSortingRepository<ProductGroupRate, Integer> {

    @Query("SELECT p FROM ProductGroupRate p WHERE p.project = :project")
    public List<ProductGroupRate> findByProjectId(@Param("project")Project project);

    @Query("SELECT p FROM ProductGroupRate p WHERE p.project = :project and p.productGroup = :productGroup")
    public List<ProductGroupRate> findByProjectAndProductGroup (@Param("project")Project project, @Param("productGroup")ProductGroup productGroup);

    @Query("SELECT p FROM ProductGroupRate p WHERE p.productGroup = :productGroup")
    public List<ProductGroupRate> findByProductGroup(@Param("productGroup")ProductGroup productGroup);
}

