
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
import com.sanyo.quote.domain.CurrencyExchRate;
import com.sanyo.quote.repository.CurrencyExchRateRepository;
import com.sanyo.quote.service.CurrencyExchRateService;

@Service("currencyExchRateService")
@Repository
@Transactional
public class DefaultCurrencyExchRateService implements CurrencyExchRateService {

	@Autowired
	private CurrencyExchRateRepository currencyExchRateRepository;

	@Transactional(readOnly=true)
	public List<CurrencyExchRate> findAll() {
		return Lists.newArrayList(currencyExchRateRepository.findAll());
	}

	@Transactional(readOnly=true)
	public CurrencyExchRate findById(Integer id) {
		return currencyExchRateRepository.findOne(id);
	}

	public CurrencyExchRate save(CurrencyExchRate currencyExchRate) {
		return currencyExchRateRepository.save(currencyExchRate);
	}

	@Transactional(readOnly=true)
	public Page<CurrencyExchRate> findAllByPage(Pageable pageable) {
		return currencyExchRateRepository.findAll(pageable);
	}

	@Override
	public List<CurrencyExchRate> findBySourceAndTarget(Currency sourceCode, Currency targetCode) {
		return currencyExchRateRepository.findBySourceAndTarget(sourceCode, targetCode);
	}
}