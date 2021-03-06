package com.sanyo.quote.service;

import com.sanyo.quote.domain.ProductGroup;
import com.sanyo.quote.domain.ProductGroupRate;
import com.sanyo.quote.domain.Project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Chuong on 11/30/2015.
 */
public interface ProductGroupRateService {
    List<ProductGroupRate> findAll();

    ProductGroupRate findById(Integer id);

    ProductGroupRate save(ProductGroupRate productGroupRate);

    Page<ProductGroupRate> findAllByPage(Pageable pageable);

    List<ProductGroupRate> findByProjectId (Integer projectId);

    List<ProductGroupRate> findByProjectIdAndProductGroupId (Integer projectId, Integer productGroupId);
    public List<ProductGroupRate> findByProjectAndProductGroup(Project project, ProductGroup productGroup);
    public List<ProductGroupRate> findByProductGroup(ProductGroup productGroup);
}
