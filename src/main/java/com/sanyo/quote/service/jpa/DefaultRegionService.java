
package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.Category;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.repository.CategoryRepository;
import com.sanyo.quote.repository.RegionRepository;
import com.sanyo.quote.service.CategoryService;
import com.sanyo.quote.service.RegionService;

@Service("regionService")
@Repository
@Transactional
public class DefaultRegionService implements RegionService {

	@Autowired
	private RegionRepository regionRepository;

	@Override
	public List<Region> findAll() {
		return Lists.newArrayList(regionRepository.findAll());
	}

	@Override
	public Region findById(Integer id) {
		return regionRepository.findOne(id);
	}

	@Override
	public Region save(Region Region) {
		return regionRepository.save(Region);
	}

	@Override
	public Page<Region> findAllByPage(Pageable pageable) {
		return regionRepository.findAll(pageable);
	}
	
	
}
