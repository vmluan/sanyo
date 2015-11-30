package com.sanyo.quote.service.jpa;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.ProductGroupRate;
import com.sanyo.quote.repository.ProductGroupRateRepository;
import com.sanyo.quote.service.ProductGroupRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Chuong on 11/30/2015.
 */
@Service("productGroupRateService")
@Repository
@Transactional
public class DefaultProductGroupRateService implements ProductGroupRateService {

    @Autowired
    private ProductGroupRateRepository productGroupRateRepository;

    @Override
    public List<ProductGroupRate> findAll() {
        return Lists.newArrayList(productGroupRateRepository.findAll());
    }

    @Override
    public ProductGroupRate findById(Integer id) {
        return productGroupRateRepository.findOne(id);
    }

    @Override
    public ProductGroupRate save(ProductGroupRate productGroupRate) {
        return productGroupRateRepository.save(productGroupRate);
    }

    @Override
    public Page<ProductGroupRate> findAllByPage(Pageable pageable) {
        return productGroupRateRepository.findAll(pageable);
    }

    @Override
    public List<ProductGroupRate> findByProjectId(Integer projectId) {
        return productGroupRateRepository.findByProjectId(projectId);
    }
}
