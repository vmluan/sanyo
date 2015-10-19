
package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.Maker;
import com.sanyo.quote.domain.UserRegionRole;
import com.sanyo.quote.repository.MakerRepository;
import com.sanyo.quote.repository.UserRegionRoleRepository;
import com.sanyo.quote.service.MakerService;

@Service("makerService")
@Repository
@Transactional
public class DefaultMakerService implements MakerService {

	@Autowired
	private MakerRepository makerRepository;

	@Override
	public List<Maker> findAll() {
		return Lists.newArrayList(makerRepository.findAll());
	}

	@Override
	public Maker findById(Integer id) {
		return makerRepository.findOne(id);
	}

	@Override
	public Maker save(Maker maker) {
		return makerRepository.save(maker);
	}

	@Override
	public Page<Maker> findAllByPage(Pageable pageable) {
		return makerRepository.findAll(pageable);
	}

	@Override
	public Maker findByIdAndFetchProductGroupMakerEagerly(Integer id) {
		return makerRepository.findByIdAndFetchProductGroupMakerEagerly(id);
	}

	@Override
	public Maker findByName(String name) {
		return makerRepository.findByName(name);
	}


}
