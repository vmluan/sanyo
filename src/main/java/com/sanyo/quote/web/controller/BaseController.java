/**
 * Copyright (c) 2015  osgicse group.
 * Filename   : BaseController.java
 * Description: 
 * @author    : Luan Vo
 * Created    : Sep 3, 2013
 */

package com.sanyo.quote.web.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;

import com.sanyo.quote.domain.Language;
import com.sanyo.quote.helper.Constants;
import com.sanyo.quote.service.LanguageService;

public class BaseController {
	final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	private LanguageService languageService;

	public String getLanguage() {
		Locale locale = LocaleContextHolder.getLocale();
		if (locale.getLanguage().equalsIgnoreCase(Constants.VIETNAMESE)) {
			return locale.getLanguage();
		} else {
			return Constants.ENGLISH_AMERICAN;
		}
	}

	protected Integer getLanguageId(String code) {
		Language language = languageService.findByCode(code);
		return language.getLanguageid();
	}
}
