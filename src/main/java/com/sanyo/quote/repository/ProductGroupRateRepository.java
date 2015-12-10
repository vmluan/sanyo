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

    @Query("SELECT p FROM ProductGroupRate p WHERE p.project = :projectId")
    public List<ProductGroupRate> findByProjectId(@Param("projectId")Integer projectId);
    
    // Luan: i think this method will not work, use the below instead.
    @Query("SELECT p FROM ProductGroupRate p WHERE p.project = :projectId and p.productGroup = :productGroupId")
    public List<ProductGroupRate> findByProjectIdAndProductGroup (@Param("projectId")Integer projectId, @Param("projectId")Integer productGroupId);
    
    public List<ProductGroupRate> findByProjectAndProductGroup(Project project, ProductGroup productGroup);
}

