package com.sanyo.quote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Language;

public interface LanguageRepository extends PagingAndSortingRepository<Language, Integer> {
	@Query("select l from Language l where l.languagecode = :languagecode")
	Language findByCode(@Param("languagecode") String code);	
}
