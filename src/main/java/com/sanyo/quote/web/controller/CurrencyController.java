package com.sanyo.quote.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sanyo.quote.domain.Currency;
import com.sanyo.quote.domain.CurrencyExchRate;
import com.sanyo.quote.domain.LabourPrice;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.CurrencyExchRateService;
import com.sanyo.quote.service.CurrencyService;
import com.sanyo.quote.web.form.Message;

@Controller
@RequestMapping(value = "/currency")
public final class CurrencyController extends CommonController{
	final Logger logger = LoggerFactory.getLogger(CurrencyController.class);
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private CurrencyService currencyService;
	
	@Autowired
	private CurrencyExchRateService currencyExchRateService;
	
	private Validator validator;
	
	public CurrencyController(){
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
	}
	
	//handle /currencies
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel, HttpServletRequest request) {
		logger.info("Currency exchange rates");
		setBreadCrumb(uiModel, "/", "Home", "/currency", "Currency");
		setHeader(uiModel, "Currency", "List of all Currency exchange rates");
		setUser(uiModel);
		return "currency/listExchRates";
	}
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createNewCurrencyExchRate(Model uiModel, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException{
		List<Currency> currencies = currencyService.findAll();
		Map<String,String> currencyList = new LinkedHashMap<String,String>();
		for(Currency currency : currencies){
			currencyList.put(currency.getCurrencyId().toString(),currency.getCurrencyName() );
		}
		uiModel.addAttribute("currencyExchRate", new CurrencyExchRate());
		uiModel.addAttribute("currencyList", currencyList);
		uiModel.addAttribute("currencies", currencies);
		return "currency/newExchRate";
	}	//create new project, save to database
	@RequestMapping(params = "form", method = RequestMethod.POST)
    public String create(@Valid CurrencyExchRate currencyExchRate, BindingResult bindingResult, Model uiModel, 
    		HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
		logger.info("Creating CurrencyExchRate");
		String errorMsg = null;
			try{
	        if (bindingResult.hasErrors()) {
	    		List<Currency> currencies = currencyService.findAll();
	    		Map<String,String> currencyList = new LinkedHashMap<String,String>();
	    		for(Currency currency : currencies){
	    			currencyList.put(currency.getCurrencyId().toString(),currency.getCurrencyName() );
	    		}
	    		uiModel.addAttribute("currencyExchRate", new CurrencyExchRate());
	    		uiModel.addAttribute("currencyList", currencyList);
	    		uiModel.addAttribute("currencies", currencies);
	    		return "currency/newExchRate";
	        }
	        Currency sourceCurrency = currencyService.findById(Integer.valueOf(currencyExchRate.getSourceCurrencyId()));
	        Currency targetCurrency = currencyService.findById(Integer.valueOf(currencyExchRate.getTargetCurrencyId()));
	        List<CurrencyExchRate> existingRates = this.findExistingRates(sourceCurrency, targetCurrency);
	        if(isOverlapped(currencyExchRate, existingRates)){
	        	throwOverlappedDateException("The date range is overlapped with existing one.");
	        }
	        this.updateExistingRates(existingRates, currencyExchRate);
	        currencyExchRate.setSourceCurrency(sourceCurrency);
	        currencyExchRate.setTargetCurrency(targetCurrency);
	        currencyExchRateService.save(currencyExchRate);
	        
		}catch(Exception e){
			errorMsg = e.getMessage();
			uiModel.addAttribute("message", new Message("error", errorMsg));
			initialize(uiModel, currencyExchRate);
			return "currency/newExchRate";
			
		}
		
		return "redirect:/currency";
    }
	private void initialize(Model uiModel, CurrencyExchRate currencyExchRate){
		List<Currency> currencies = currencyService.findAll();
		Map<String,String> currencyList = new LinkedHashMap<String,String>();
		for(Currency currency : currencies){
			currencyList.put(currency.getCurrencyId().toString(),currency.getCurrencyName() );
		}
		uiModel.addAttribute("currencyExchRate", currencyExchRate);
		uiModel.addAttribute("currencyList", currencyList);
		
	}
	private List<CurrencyExchRate> findExistingRates(Currency source, Currency target){
		return currencyExchRateService.findBySourceAndTarget(source, target);
	}
	private boolean isOverlapped(CurrencyExchRate newObj, List<CurrencyExchRate> existingRates){
		boolean result = false;
		for(CurrencyExchRate cr : existingRates){
			
			if(cr.getEndDate() != null){
				if(newObj.getStartDate().equals(cr.getStartDate())
						|| newObj.getEndDate().equals(cr.getEndDate())){
					result = true;
					break;
				}
				// or new start date is between old start date and old end date.
				else if(newObj.getStartDate().before(cr.getEndDate())
					&& newObj.getStartDate().after(cr.getStartDate())){
					result = true;
					break;
				}
				// or new end date is between old start date and old end date.
				if(newObj.getEndDate() != null){
					if(newObj.getEndDate().before(cr.getEndDate())
							&& newObj.getEndDate().after(cr.getStartDate())){
						result = true;
						break;
					}
				}
				
			}
			
		}
		return result;
	}
	private void updateExistingRates(List<CurrencyExchRate> rates, CurrencyExchRate json){
		for(CurrencyExchRate cr : rates){
			if(cr.getEndDate() == null 
					&& cr.getStartDate().before(json.getStartDate())){
				//update end date of current price
				cr.setEndDate(json.getStartDate()); //- 1 later
				currencyExchRateService.save(cr);
			}
		}
	}
	@RequestMapping(value = "/getListJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getProductsJson(@RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
//		List<CurrencyExchRate> currencyExchRates =  currencyExchRateService.findAll();
		List<CurrencyExchRate> currencyExchRates =  currencyExchRateService.findLatestList();
		String result = Utilities.jSonSerialization(currencyExchRates);
		return result;
	}	
	// handle screen for updating. Just display form for form edit.
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		String errorMsg = null;
		try{
			CurrencyExchRate currencyExchRate = currencyExchRateService.findById(id);
	        initialize(uiModel, currencyExchRate);
		}catch(Exception e){
			errorMsg = e.getMessage();
		}
		uiModel.addAttribute("message", new Message("error", errorMsg));
		
		return "currency/newExchRate";
	}
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
	@Transactional
    public String update(@ModelAttribute("currencyExchRate") CurrencyExchRate currencyExchRate, @PathVariable Integer id, Model uiModel, 
    		HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale, BindingResult bindingResult) {
		logger.info("Updating currencyExchRate");
		String errorMsg = null;
		try{
			 Set<ConstraintViolation<CurrencyExchRate>> violations = validator.validate(currencyExchRate);
		     
		    for (ConstraintViolation<CurrencyExchRate> violation : violations)
		    {
		        String propertyPath = violation.getPropertyPath().toString();
		        String message = violation.getMessage();
		        
		        // Add JSR-303 errors to BindingResult
		        // This allows Spring to display them in view via a FieldError
		        bindingResult.addError(new FieldError("user",propertyPath,
		                               "Invalid "+ propertyPath + "(" + message + ")"));
		    }
			if (bindingResult.hasErrors()) {
				uiModel.addAttribute("message", new Message("error", messageSource.getMessage("project_save_fail", new Object[]{}, locale)));
				initialize(uiModel, currencyExchRate);
	            return "currency/newExchRate";
		        }    
	        uiModel.asMap().clear();
	        currencyExchRate.setId(id);
	        uiModel.addAttribute("message", new Message("success", messageSource.getMessage("project_save_success", new Object[]{}, locale)));        
	        setBreadCrumb(uiModel, "/currency", "Projects", "", "Project Detail");
	        setHeader(uiModel, "Project Detail", "Contains detail information including regions and assigned users");
	        
	        Currency sourceCurrency = currencyService.findById(Integer.valueOf(currencyExchRate.getSourceCurrencyId()));
	        Currency targetCurrency = currencyService.findById(Integer.valueOf(currencyExchRate.getTargetCurrencyId()));
	        currencyExchRate.setSourceCurrency(sourceCurrency);
	        currencyExchRate.setTargetCurrency(targetCurrency);
			List<CurrencyExchRate> existingRates = this.findExistingRates(sourceCurrency, targetCurrency);
	        if(isOverlapped(currencyExchRate, existingRates)){
	        	throwOverlappedDateException("The date range is overlapped with existing one.");
	        }
	        this.updateExistingRates(existingRates, currencyExchRate);
	        initialize(uiModel, currencyExchRate);
	        currencyExchRateService.save(currencyExchRate);
		}catch (Exception e){
			errorMsg = e.getMessage();
			uiModel.addAttribute("message", new Message("error", errorMsg));
			initialize(uiModel, currencyExchRate);
			return "currency/newExchRate";
		}
        return "currency/newExchRate";
        
    }
	@RequestMapping(value = "/getHistJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getLocationsJson(@RequestParam(value="currencyId", required=true) Integer id,
			@RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		CurrencyExchRate currencyExchRate = currencyExchRateService.findById(id);
		List<CurrencyExchRate> list = currencyExchRateService.findBySourceAndTarget(currencyExchRate.getSourceCurrency(), currencyExchRate.getTargetCurrency());
		String result = Utilities.jSonSerialization(list);
		return result;
	}
}
