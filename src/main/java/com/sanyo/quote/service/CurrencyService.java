package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Currency;
import com.sanyo.quote.domain.CurrencyExchRate;

public interface CurrencyService {

	List<Currency> findAll();
	
	Currency findById(Integer id);
	
	Currency save(Currency currency);
	
	Page<Currency> findAllByPage(Pageable pageable);

}
