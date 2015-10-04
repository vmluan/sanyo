package com.sanyo.quote.web.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sanyo.quote.domain.Product;

@Controller
@RequestMapping(value = "/quotation")
public class Quotation {

	@RequestMapping(method = RequestMethod.GET)
	public String getQuotationPage(Model ciModel,@RequestParam(value="lang", required=false)String id) {
		return "quotation/index";
	}

}
