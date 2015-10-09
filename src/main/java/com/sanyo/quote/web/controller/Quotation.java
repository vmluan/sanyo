package com.sanyo.quote.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sanyo.quote.domain.Encounter;
import com.sanyo.quote.repository.EncounterRepository;

@Controller
@RequestMapping(value = "/quotation")
public class Quotation {
	final Logger logger = LoggerFactory.getLogger(Quotation.class);
	@Autowired
	private EncounterRepository encounterRepository;

	@RequestMapping(method = RequestMethod.GET)
	public String getQuotationPage(Model uiModel) {
		return "quotation/index";
	}
	
	@RequestMapping(value = "/{id}/addquotation", params = "form", method = RequestMethod.GET)
	public String showRegions(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest){
		Encounter encounter = new Encounter();
		return "quotation/create";
	}

}
