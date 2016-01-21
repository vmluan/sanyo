package com.sanyo.quote.service.jpa;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.ProductGroup;
import com.sanyo.quote.domain.ProductGroupRate;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.repository.ProductGroupRateRepository;
import com.sanyo.quote.repository.ProductGroupRepository;
import com.sanyo.quote.repository.ProjectRepository;
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
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProductGroupRepository productGroupRepository;

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
        Project project = projectRepository.findOne(projectId);
        return productGroupRateRepository.findByProjectId(project);
    }

    @Override
    public List<ProductGroupRate> findByProjectIdAndProductGroupId(Integer projectId, Integer productGroupId) {
        Project project = projectRepository.findOne(projectId);
        ProductGroup productGroup = productGroupRepository.findOne(productGroupId);
        return productGroupRateRepository.findByProjectIdAndProductGroup(project,productGroup);
    }

	@Override
	public List<ProductGroupRate> findByProjectAndProductGroup(Project project, ProductGroup productGroup) {
		return productGroupRateRepository.findByProjectAndProductGroup(project, productGroup);
	}

	@Override
	public List<ProductGroupRate> findByProductGroup(ProductGroup productGroup) {
		return productGroupRateRepository.findByProductGroup(productGroup);
	}
}
