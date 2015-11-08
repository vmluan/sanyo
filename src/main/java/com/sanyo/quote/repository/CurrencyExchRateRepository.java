package com.sanyo.quote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Currency;
import com.sanyo.quote.domain.CurrencyExchRate;

public interface CurrencyExchRateRepository extends PagingAndSortingRepository<CurrencyExchRate, Integer> {
	@Query("SELECT c FROM CurrencyExchRate c WHERE c.sourceCurrency = :sourceCode and c.targetCurrency = :targetCode")
	public List<CurrencyExchRate> findBySourceAndTarget(@Param("sourceCode") Currency sourceCode,
			@Param("targetCode") Currency targetCode);
}
