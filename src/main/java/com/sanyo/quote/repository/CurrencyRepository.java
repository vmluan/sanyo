package com.sanyo.quote.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sanyo.quote.domain.Currency;


public interface CurrencyRepository extends PagingAndSortingRepository<Currency, Integer> {
	public Currency findByCurrencyCode(String currencyCode);
}
