
package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.ProductGroup;
import com.sanyo.quote.repository.MakerRepository;
import com.sanyo.quote.repository.ProductGroupRepository;
import com.sanyo.quote.service.ProductGroupService;

@Service("productGroupService")
@Repository
@Transactional
public class DefaultProductGroupService implements ProductGroupService {

	@Autowired
	private ProductGroupRepository productGroupRepository;

	@Override
	public List<ProductGroup> findAll() {
		return Lists.newArrayList(productGroupRepository.findAll());
	}

	@Override
	public ProductGroup findById(Integer id) {
		return productGroupRepository.findOne(id);
	}

	@Override
	public ProductGroup save(ProductGroup productGroup) {
		return productGroupRepository.save(productGroup);
	}

	@Override
	public Page<ProductGroup> findAllByPage(Pageable pageable) {
		return productGroupRepository.findAll(pageable);
	}

	@Override
	public ProductGroup findByIdAndFetchProductGroupMakerEagerly(Integer id) {
		return productGroupRepository.findByIdAndFetchProductGroupMakerEagerly(id);
	}

	@Override
	public ProductGroup findByIdAndFetchProductsEagerly(Integer id) {
		return productGroupRepository.findByIdAndFetchProductsEagerly(id);
	}

	@Override
	public ProductGroup findByGroupName(String groupName) {
		return productGroupRepository.findByGroupName(groupName);
	}

	@Override
	public ProductGroup findByGroupCode(String groupCode) {
		return productGroupRepository.findByGroupCode(groupCode);
	}



}
