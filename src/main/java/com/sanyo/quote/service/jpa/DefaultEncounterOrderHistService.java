
package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.EncounterOrderHist;
import com.sanyo.quote.repository.EncounterOrderHistRepository;
import com.sanyo.quote.service.EncounterOrderHistService;

@Service("encounterOrderHistService")
@Repository
@Transactional
public class DefaultEncounterOrderHistService implements EncounterOrderHistService {

	@Autowired
	private EncounterOrderHistRepository encounterRepository;

	@Transactional(readOnly=true)
	public List<EncounterOrderHist> findAll() {
		return Lists.newArrayList(encounterRepository.findAll());
	}

	@Transactional(readOnly=true)
	public EncounterOrderHist findById(Integer id) {
		return encounterRepository.findOne(id);
	}

	public EncounterOrderHist save(EncounterOrderHist th_encounter) {
		return encounterRepository.save(th_encounter);
	}

	@Transactional(readOnly=true)
	public Page<EncounterOrderHist> findAllByPage(Pageable pageable) {
		return encounterRepository.findAll(pageable);
	}

	@Override
	public void delete(Integer id) {
		encounterRepository.delete(id);
		
	}

	@Override
	public void delte(EncounterOrderHist EncounterOrderHist) {
		encounterRepository.delete(EncounterOrderHist);
		
	}

}
