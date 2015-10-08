package com.sanyo.quote.web.controller.admin;

import java.util.List;
import java.util.Locale;
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

import com.sanyo.quote.domain.Category;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.CategoryService;
import com.sanyo.quote.web.controller.CommonController;
import com.sanyo.quote.web.form.Message;
import com.sanyo.quote.web.util.UrlUtil;

@RequestMapping("/admin/categories")
@Controller
public class CategoryController extends CommonController {
	final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	@Autowired
	MessageSource messageSource;
	@Autowired
	CategoryService categoryService;
	private Validator validator;
	
	public CategoryController(){
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
	}
	
	//handle /admin/categories
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		logger.info("Listing categories");

		List<Category> categories = categoryService.findAll();
		uiModel.addAttribute("categories", categories);
		setBreadCrumb(uiModel, "/", "Home", "", "Categories");
		return "categories/list";
	}
	
	// handle screen for updating. Just display form for form edit.
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		Category category = categoryService.findById(id);
        uiModel.addAttribute("category", category);
        resetCategories(uiModel,category, categoryService.findParents());
        setBreadCrumb(uiModel, "/admin/categories", "Categories", "", "Update Category");
        return "categories/update";
	}
	
	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
		Category category = new Category();
        uiModel.addAttribute("category", category);
        resetCategories(uiModel,category, categoryService.findParents());
        setBreadCrumb(uiModel, "/admin/categories", "Categories", "", "Create Category");
        return "categories/create";
	}
	//create new category, save to database
	@RequestMapping(params = "form", method = RequestMethod.POST)
    public String create(@Valid Category category, BindingResult bindingResult, Model uiModel, 
    		HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale) {
		logger.info("Creating category");
        if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", new Message("error", messageSource.getMessage("category_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("category", category);
            resetCategories(uiModel,category, categoryService.findParents());
            return "categories/create";
        }

        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("category_save_success", new Object[]{}, locale)));
        categoryService.save(category);
        return "redirect:/admin/categories/" + UrlUtil.encodeUrlPathSegment(category.getCategoryId().toString(), httpServletRequest) + "?form";
    }
	
	//update an existing group, save to database
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
	@Transactional
    public String update(@ModelAttribute("category") Category category, @PathVariable Integer id, Model uiModel, 
    		HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale, BindingResult bindingResult) {
		logger.info("Updating category");
		 Set<ConstraintViolation<Category>> violations = validator.validate(category);
	     
	    for (ConstraintViolation<Category> violation : violations)
	    {
	        String propertyPath = violation.getPropertyPath().toString();
	        String message = violation.getMessage();
	        // Add JSR-303 errors to BindingResult
	        // This allows Spring to display them in view via a FieldError
	        bindingResult.addError(new FieldError("user",propertyPath,
	 
	                               "Invalid "+ propertyPath + "(" + message + ")"));
	    }
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", new Message("error", messageSource.getMessage("category_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("category", category);
            resetCategories(uiModel, category, categoryService.findParents());
            return "categories/update";
	        }    
        uiModel.asMap().clear();
        category.setCategoryId(id);
        redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("category_save_success", new Object[]{}, locale)));        
        uiModel.addAttribute("category", category);
        resetCategories(uiModel,category, categoryService.findParents());
        
        String parentCategoryId = category.getParentCategoryId();
        if(parentCategoryId != null && !parentCategoryId.equals(""))
        	category.setParentCategory(categoryService.findById(Integer.valueOf(parentCategoryId)));
        
        categoryService.save(category);
        return "categories/update";
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
		
		
		// Constructs page request for current page
//		PageRequest pageRequest = null;
//		pageRequest = new PageRequest(pagenum, pagesize);

		
		List<Category> categories  = categoryService.findAll();
		String result = Utilities.jSonSerialization(categories);
		return result;
	}
	private void resetCategories(Model uiModel,Category category, List<Category> categories){
		uiModel.addAttribute("parentCategories", categories);
		Category parentCategory = category.getParentCategory();
		if(parentCategory != null)
			category.setParentCategoryId(parentCategory.getCategoryId().toString());
	}
	

}
