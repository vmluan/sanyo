
package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.Location;
import com.sanyo.quote.repository.LocationRepository;
import com.sanyo.quote.service.LocationService;

@Service("locationService")
@Repository
@Transactional
public class DefaultLocationService implements LocationService {

	@Autowired
	private LocationRepository locationRepository;

	@Override
	public List<Location> findAll() {
		return Lists.newArrayList(locationRepository.findAll());
	}

	@Override
	public Location findById(Integer id) {
		return locationRepository.findOne(id);
	}

	@Override
	public Location save(Location Location) {
		return locationRepository.save(Location);
	}

	@Override
	public Page<Location> findAllByPage(Pageable pageable) {
		return locationRepository.findAll(pageable);
	}

	@Override
	public Location findByIdAndFetchRegionsEagerly(Integer id) {
		return locationRepository.findByIdAndFetchRegionsEagerly(id);
	}

}
