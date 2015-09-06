package com.sanyo.quote.service;

import java.util.List;

import com.sanyo.quote.domain.Language;

public interface LanguageService {

	List<Language> findAll();
	
	Language findById(Integer id);
	
	Language findByCode(String code);
	
}
