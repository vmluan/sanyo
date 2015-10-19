
package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.ProductGroupMaker;
import com.sanyo.quote.repository.ProductGroupMakerRepository;
import com.sanyo.quote.service.ProductGroupMakerService;

@Service("productGroupMakerService")
@Repository
@Transactional
public class DefaultProductGroupMakerService implements ProductGroupMakerService {

	@Autowired
	private ProductGroupMakerRepository productGroupMakerRepository;

	@Override
	public List<ProductGroupMaker> findAll() {
		return Lists.newArrayList(productGroupMakerRepository.findAll());
	}

	@Override
	public ProductGroupMaker findById(Integer id) {
		return productGroupMakerRepository.findOne(id);
	}

	@Override
	public ProductGroupMaker save(ProductGroupMaker productGroupMaker) {
		return productGroupMakerRepository.save(productGroupMaker);
	}

	@Override
	public Page<ProductGroupMaker> findAllByPage(Pageable pageable) {
		return productGroupMakerRepository.findAll(pageable);
	}
}
