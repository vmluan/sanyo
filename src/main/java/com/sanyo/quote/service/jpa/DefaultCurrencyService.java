
package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.Currency;
import com.sanyo.quote.repository.CurrencyRepository;
import com.sanyo.quote.service.CurrencyService;

@Service("currencyService")
@Repository
@Transactional
public class DefaultCurrencyService implements CurrencyService {

	@Autowired
	private CurrencyRepository currencyRepository;

	@Transactional(readOnly=true)
	public List<Currency> findAll() {
		return Lists.newArrayList(currencyRepository.findAll());
	}

	@Transactional(readOnly=true)
	public Currency findById(Integer id) {
		return currencyRepository.findOne(id);
	}

	public Currency save(Currency currency) {
		return currencyRepository.save(currency);
	}

	@Transactional(readOnly=true)
	public Page<Currency> findAllByPage(Pageable pageable) {
		return currencyRepository.findAll(pageable);
	}

	@Override
	public Currency findByCurrencyCode(String currencyCode) {
		return currencyRepository.findByCurrencyCode(currencyCode);
	}
}
