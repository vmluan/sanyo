package com.sanyo.quote.repository;

import java.util.Date;
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
	
	@Query("SELECT distinct c.sourceCurrency, c.targetCurrency FROM CurrencyExchRate c group by c.sourceCurrency, c.targetCurrency")
	public List<Object> findLatestList();
	
	@Query("SELECT max (c.startDate) FROM CurrencyExchRate c WHERE c.sourceCurrency = :sourceCode and c.targetCurrency = :targetCode")
	public Object findLatestStartDate(@Param("sourceCode") Currency sourceCode,
			@Param("targetCode") Currency targetCode);
	
	@Query("SELECT distinct c FROM CurrencyExchRate c WHERE c.sourceCurrency = :sourceCode and c.targetCurrency = :targetCode and c.startDate =:startDate")
	public List<Object> findLatestPair(@Param("sourceCode") Currency sourceCode,
			@Param("targetCode") Currency targetCode, @Param("startDate") Date startDate);
}
