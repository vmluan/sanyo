
package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.Encounter;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.repository.EncounterRepository;
import com.sanyo.quote.service.EncounterService;

@Service("encounterService")
@Repository
@Transactional
public class DefaultEncounterService implements EncounterService {

	@Autowired
	private EncounterRepository encounterRepository;

	@Transactional(readOnly=true)
	public List<Encounter> findAll() {
		return Lists.newArrayList(encounterRepository.findAll());
	}

	@Transactional(readOnly=true)
	public Encounter findById(Integer id) {
		return encounterRepository.findOne(id);
	}

	public Encounter save(Encounter th_encounter) {
		return encounterRepository.save(th_encounter);
	}

	@Transactional(readOnly=true)
	public Page<Encounter> findAllByPage(Pageable pageable) {
		return encounterRepository.findAll(pageable);
	}

	@Override
	public void delete(Integer id) {
		encounterRepository.delete(id);
		
	}

	@Override
	public void delte(Encounter encounter) {
		encounterRepository.delete(encounter);
		
	}

	@Override
	public List<Encounter> getEncountersByRegion(Region region) {
		return encounterRepository.getEncountersByRegion(region);
	}
}
