
package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.LocationOrderHist;
import com.sanyo.quote.repository.LocationOrderHistRepository;
import com.sanyo.quote.service.LocationOrderHistService;

@Service("locationOrderHistService")
@Repository
@Transactional
public class DefaultLocationOrderHistService implements LocationOrderHistService {

	@Autowired
	private LocationOrderHistRepository locationRepository;

	@Transactional(readOnly=true)
	public List<LocationOrderHist> findAll() {
		return Lists.newArrayList(locationRepository.findAll());
	}

	@Transactional(readOnly=true)
	public LocationOrderHist findById(Integer id) {
		return locationRepository.findOne(id);
	}

	public LocationOrderHist save(LocationOrderHist th_encounter) {
		return locationRepository.save(th_encounter);
	}

	@Transactional(readOnly=true)
	public Page<LocationOrderHist> findAllByPage(Pageable pageable) {
		return locationRepository.findAll(pageable);
	}

	@Override
	public void delete(Integer id) {
		locationRepository.delete(id);
		
	}

	@Override
	public void delte(LocationOrderHist LocationOrderHist) {
		locationRepository.delete(LocationOrderHist);
		
	}

}
