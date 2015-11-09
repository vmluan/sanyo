package com.sanyo.quote.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Currency;
import com.sanyo.quote.domain.CurrencyExchRate;

public interface CurrencyExchRateService {

	List<CurrencyExchRate> findAll();
	
	CurrencyExchRate findById(Integer id);
	
	CurrencyExchRate save(CurrencyExchRate currencyExchRate);
	
	Page<CurrencyExchRate> findAllByPage(Pageable pageable);
	List<CurrencyExchRate> findBySourceAndTarget(Currency sourceCode,
			Currency targetCode);
	List<CurrencyExchRate> findLatestList();
	Date findLatestStartDate(Currency sourceCode,Currency targetCode);
	CurrencyExchRate findLatestPair(Currency sourceCode, Currency targetCode, Date startDate);
}
