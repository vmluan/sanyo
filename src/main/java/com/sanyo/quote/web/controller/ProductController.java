package com.sanyo.quote.web.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sanyo.quote.domain.Category;
import com.sanyo.quote.domain.CategoryJson;
import com.sanyo.quote.domain.LabourPrice;
import com.sanyo.quote.domain.Maker;
import com.sanyo.quote.domain.Product;
import com.sanyo.quote.domain.ProductGroup;
import com.sanyo.quote.domain.ProductGroupMaker;
import com.sanyo.quote.domain.ProductJson;
import com.sanyo.quote.helper.ProductHepler;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.CategoryService;
import com.sanyo.quote.service.MakerService;
import com.sanyo.quote.service.PriceService;
import com.sanyo.quote.service.ProductGroupMakerService;
import com.sanyo.quote.service.ProductGroupService;
import com.sanyo.quote.service.ProductService;
import com.sanyo.quote.web.form.FileBean;

@Controller
@RequestMapping(value = "/products")
public class ProductController extends CommonController {
	final Logger logger = LoggerFactory.getLogger(ProductController.class);
	final String UPLOAD_DIRECTORY = "/images/t-shirts/";
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductGroupService productGroupService;
	
	@Autowired
	private PriceService priceService;
	
	@Autowired
	private MakerService makerService;
	
	@Autowired
	private ProductGroupMakerService productGroupMakerService;

	@RequestMapping(method = RequestMethod.GET)
	public String getProductPage(Model ciModel,@RequestParam(value="lang", required=false)String id) {
		Locale locale = LocaleContextHolder.getLocale();
		
		if(StringUtils.isNotEmpty(id)){
			if(id.equalsIgnoreCase(com.sanyo.quote.helper.Constants.VIETNAMESE))
				locale.setDefault(new Locale(id));
		}
		List<Product> products = productService.findAll();
		ciModel.addAttribute("products", products);
		//fileBean
		FileBean fileBean = new FileBean();
		ciModel.addAttribute("fileBean", fileBean);
		
		addProductHomeLink(ciModel);
		
		return "products/list";
	}
	
	private void addProductHomeLink(Model model){
		resetLinks();
		addToLinks("Product List", "/products");
		setModel(model);
	}
	@RequestMapping(value = "/setjson")
	public String generateJsonFile(HttpServletRequest httpServletRequest){
		
//		List<Product> products = productService.findAll();
//		
//		ProductHepler productHepler = new ProductHepler();
//		String location = httpServletRequest.getSession().getServletContext().getRealPath("/");
//		productHepler.generateProductJson(products, location);
		generateProductFiles(httpServletRequest);
		return "redirect:/products"; 
	}
	
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createNewProduct(Model uiModel, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException{
		
		System.out.println("============================== new product");
		Product product = new Product();
		product.setFile(null);
		uiModel.addAttribute("product", product);
		
		List<Product> products =  productService.findAll();
		
		uiModel.addAttribute("products", products);
		
		List<Category> categories = categoryService.findAll();
		uiModel.addAttribute("categories", categories);
		
		httpServletRequest.setCharacterEncoding("UTF-8");
		addProductHomeLink(uiModel);
		addToLinks("New Product", "");
		return "products/new";
	}
	
//	@RequestMapping(params = "form", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
//	public String saveNewProduct(@Valid Product product, BindingResult bindingResult,
//			Model uiModel, HttpServletRequest httpServletRequest,
//			RedirectAttributes redirectAttributes, Locale locale){
//		try {
//			httpServletRequest.setCharacterEncoding("UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if (bindingResult.hasErrors()) {
//			uiModel.addAttribute("product", product);
//			return "products/new";
//		}
//		
//		String []categoriesList = product.getCategoriesList();
//		if(categoriesList != null){
//			ArrayList<Category> categories = new ArrayList<Category>();
//			for (int i=0; i< categoriesList.length; i++){
//				Category category = categoryService.findById(Integer.valueOf(categoriesList[i]));
//				categories.add(category);
//			}
//			product.setCategories(categories);
//		}
//		
//        String productPriceWrapper = product.getProductPriceWrapper();
//        if(productPriceWrapper == null || productPriceWrapper.equals(""))
//        	productPriceWrapper = httpServletRequest.getParameter("productPriceWrapper");
//        productPriceWrapper = productPriceWrapper.replace(",", "").replace(" ", "").replace(".", "");
//        
//        long price = Long.valueOf(productPriceWrapper);
////        product.setProductPrice(price);
//        
//        //handle attachments
//        String fileName = "";
//        fileName = saveImages(httpServletRequest);
//		
//		product.setPicLocation(fileName);
//		redirectAttributes.addFlashAttribute(
//				"message","Them SP thanh cong");
//		
//		
//		
//		
//		productService.save(product);
//		generateProductFiles(httpServletRequest);
//		uiModel.asMap().clear();
//		return "redirect:products?form";
//	}
	
	@RequestMapping(params = "form", method = RequestMethod.POST)
	@ResponseBody
	public String saveNewProduct(@RequestBody final ProductJson productJson, Model uiModel, HttpServletRequest httpServletRequest){
		logger.info("========== saving new product");
		logger.info("========== startDate = " + productJson.getStartDate());
		String errorMsg = null;
//		if (bindingResult.hasErrors()) {
//			uiModel.addAttribute("product", product);
//			return "products/new";
//		}
		try{
			saveProduct(productJson);
		}catch(DataAccessException e){
			if (e.getCause() != null 
					&& e.getCause() instanceof ConstraintViolationException){
				ConstraintViolationException cv = (ConstraintViolationException) e.getCause();
				errorMsg = cv.getCause().getMessage();
				
				
			}
			else
				errorMsg = e.getMessage();
		}catch(Exception e){
			System.out.println("==================");
			e.printStackTrace();
		}
		//return message in case of error only
		// return null when it proceeds successfully
		return errorMsg;
	}
//	private boolean isOverlapped(ProductJson json, List<LabourPrice> labourPrices){
//		boolean result = false;
//		for(LabourPrice lb : labourPrices){
//			if(lb.getExpiredDate() != null
//					&& json.getStartDate().before(lb.getExpiredDate())
//					&& json.getEndDate() != null
//					&& json.getEndDate().after(lb.getExpiredDate())){
//				result = true;
//				break;
//			}
//		}
//		return result;
//	}
//	private void throwOverlappedDateException(String message) throws Exception{
//		Exception e = new Exception(message);
//		throw e;
//	}
	private void saveLabourPrice(ProductJson json,  Product product) throws Exception{
		LabourPrice labourPrice = null;
		List<LabourPrice> labourPrices = productService.findLabourPrices(json.getProductID());
		if(labourPrices == null || labourPrices.size() ==0){
			labourPrice = new LabourPrice();
		}
		else{
			//update existing labour prices
			//check if there is any overlapped date range.
			//if yes, raise error message to aware user. he has to update date range on gui.
			//if no, proceed to update end date of latest record and insert new row.
			for(LabourPrice lb : labourPrices){
				if(json.getStartDate().equals(lb.getIssuedDate())
						&& json.getEndDate() != null && lb.getExpiredDate() != null
						&& json.getEndDate().equals(lb.getExpiredDate())){
					labourPrice = lb;
				}else if(lb.getExpiredDate() != null
						&& json.getStartDate().after(lb.getExpiredDate())){
					
				} else if(lb.getExpiredDate() == null 
						&& lb.getIssuedDate().before(json.getStartDate())){
					//update end date of current price
					lb.setExpiredDate(json.getStartDate()); //- 1 later
					priceService.save(lb);
					labourPrice = new LabourPrice();
				} else if(lb.getExpiredDate() == null 
						&& lb.getIssuedDate().after(json.getStartDate())){
					if(json.getEndDate() == null
							|| json.getEndDate().after(lb.getIssuedDate())){
						throwOverlappedDateException("The date range is overlapped with existing one.");
						
					}
					
				}
				else if(lb.getExpiredDate() != null 
						&&json.getStartDate().before(lb.getExpiredDate())
						&& (json.getEndDate() == null 
							||json.getEndDate().after(lb.getExpiredDate())
							)
						){
					//overlapped date range. Should raise exeption here
					throwOverlappedDateException("The date range is overlapped with existing one.");
					
				}
				else
					labourPrice = new LabourPrice();
			}
		}
		if(labourPrice == null)
			labourPrice = new LabourPrice();
		labourPrice.setIssuedDate(json.getStartDate());
		labourPrice.setExpiredDate(json.getEndDate());
//		labourPrice.setOutSalePrice(json.getLabour());
		labourPrice.setLabour(json.getLabour());
		labourPrice.setMax_w_o_tax_usd(json.getMat_w_o_Tax_USD());
		labourPrice.setMax_w_o_tax_vnd(json.getMat_w_o_Tax_VND());
		labourPrice.setProduct(product);
		priceService.save(labourPrice);
	}
	private boolean saveProduct(ProductJson json) throws Exception{
		Product product;
		if(json != null && json.getProductID() != null && json.getProductID() > 0){
			product = productService.findById(json.getProductID());
//			List<LabourPrice> labourPrices = productService.findLabourPrices(json.getProductID());
//			boolean isOverlapped = isOverlapped(json, labourPrices);
//			if(isOverlapped){
//				return false;
//			}
			
		}else{
			product = new Product();
		}
		
		product.setDiscount_rate(json.getDiscount_rate());
		product.setImp_Tax(json.getImp_Tax());
		product.setLabour(json.getLabour());
		product.setLastModifiedBy(Utilities.getCurrentUser().getUsername());
		product.setLastUpdated(new Date());
		product.setProductCode(json.getProductCode());
		product.setMat_w_o_Tax_USD(json.getMat_w_o_Tax_USD());
		product.setMat_w_o_Tax_VND(json.getMat_w_o_Tax_VND());
		product.setStartDate(json.getStartDate());
		product.setEndDate(json.getEndDate());
		product.setSpecification(json.getSpecification());
		product.setUnit(json.getUnit());
		if(json.getProductGroup() != null){
			ProductGroup pg = productGroupService.findById(json.getProductGroup().getGroupId());
			product.setProductGroup(pg);
		}
		if(json.getCategories() != null && json.getCategories().size() >0){
			List<Category> categories = new ArrayList<Category>();
			for(CategoryJson cate : json.getCategories()){
				Category category = categoryService.findById(cate.getCategoryId());
				categories.add(category);
			}
			product.setCategories(categories);
		}
		product.setProductName(json.getProductName());
		
		if(json.getMakerId() != null){
			Maker maker = makerService.findById(json.getMakerId());
			if(maker != null)
				product.setMaker(maker);
		}
		product = productService.save(product);
//		saveLabourPrice(json, product);
		return true;
	}
	
	@RequestMapping(value = "/getproductsjson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getProductsJson(@RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="productGroupCode", required=false) String productGroupCode
			, @RequestParam(value="makerId", required=false) String makerId
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		if(productGroupCode != null && !productGroupCode.equalsIgnoreCase("")
				&& makerId != null && !makerId.equalsIgnoreCase("")){
			ProductGroup pg = productGroupService.findByGroupCode(productGroupCode);
			Maker maker = makerService.findById(Integer.valueOf(makerId));
			
			if(pg == null || maker == null)
				return "[]";
			else{
				List<Product> productsOfPG = productService.findByProductGroupAndMaker(pg, maker);
				return Utilities.jSonSerialization(productsOfPG);
			}
		}
		List<Product> products =  productService.findAll();
		System.out.println(products);
		String result = Utilities.jSonSerialization(products);
		//httpServletResponse.setContentType("application/json; charset=UTF-8");
		return result;
	}
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Integer id, Model uiModel) {
		Product product = productService.findById(id);
		List<Category> categories = productService.findCategories(id);
		uiModel.addAttribute("product", product);
		uiModel.addAttribute("categories", categories);
		addProductHomeLink(uiModel);
		addToLinks("Update Product", "");
        return "products/update";
	}
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
    public String update(@RequestBody final ProductJson productJson, @PathVariable Integer id, Model uiModel, 
    		HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale ) {
		logger.info("Updating product");
		String errorMsg = null;
		try{
			saveProduct(productJson);
		}catch(DataAccessException e){
			errorMsg = "There is exception";
			if (e.getCause() != null 
					&& e.getCause() instanceof ConstraintViolationException){
				ConstraintViolationException cv = (ConstraintViolationException) e.getCause();
				errorMsg = cv.getCause().getMessage();
				
				
			}else if(e.getCause() != null && e.getCause() instanceof GenericJDBCException){
				GenericJDBCException genericJDBCException = (GenericJDBCException) e.getCause();
				if(genericJDBCException.getCause() != null && genericJDBCException.getCause() instanceof BatchUpdateException){
					errorMsg = genericJDBCException.getCause().getMessage();
					BatchUpdateException batchUpdateException = (BatchUpdateException) genericJDBCException.getCause();
					errorMsg = batchUpdateException.getMessage();
				}
				
				
			}
			else
				errorMsg = e.getMessage();
				
		}catch(Exception e){
			System.out.println("==================");
			e.printStackTrace();
			errorMsg = e.getMessage();
		}
		
		return errorMsg;

    }
	@ResponseBody
	@RequestMapping(value = "/{id}", params = "delete", method = RequestMethod.GET)
    public String deleteProduct(@PathVariable("id") String id, Model uiModel, HttpServletRequest httpServletRequest) {
       // uiModel.addAttribute("template", templateService.findById(id));
		System.out.println("================== " + id);
		Product product = productService.findById(Integer.valueOf(id));
		productService.deleteProduct(product);
		generateProductFiles(httpServletRequest);
		return "success";
       
	}
	private void generateProductFiles(HttpServletRequest request){
		List<Product> products = productService.findAll();
		ProductHepler productHepler = new ProductHepler();
		String location = request.getSession().getServletContext().getRealPath("/");
		productHepler.generateProductJson(products, location);
	}
	private String saveImages(HttpServletRequest httpServletRequest){
		
        MultiValueMap<String, MultipartFile> fileMap = ((MultipartHttpServletRequest)httpServletRequest).getMultiFileMap();
        Iterator<String> fileNameIterator = fileMap.keySet().iterator();
        String fileName = "";
        while(fileNameIterator.hasNext()) {
        	fileName = fileNameIterator.next();
        }
        System.out.println("======== file name is " + fileName);
		MultipartFile file =  ((MultipartHttpServletRequest)httpServletRequest).getFile(fileName);
		
		String mime_type = file.getContentType();
		
		System.out.println("======================= file type is " + mime_type);
		if (mime_type.substring(0, 5).equalsIgnoreCase("image")){
			fileName = file.getOriginalFilename();
			String filePath = httpServletRequest.getSession().getServletContext().getRealPath("/");
			String resultFile = filePath +  UPLOAD_DIRECTORY + fileName;
			
			File multipartFile = new File(resultFile);
			
			try {
				file.transferTo(multipartFile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
			fileName = "";

		return fileName;
	}
	@RequestMapping(value = "/getproductHistJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getLocationsJson(@RequestParam(value="productID", required=true) Integer productID,
			@RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		List<LabourPrice> labourPrices = productService.findLabourPrices(productID);
		String result = Utilities.jSonSerialization(labourPrices);
		return result;
	}
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String importProductFromExcel(@ModelAttribute("fileBean") FileBean fileBean, BindingResult bindingResult,
			Model uiModel,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{
		
//		System.out.println("=============== file parm is " + fileParm.toString());

        MultiValueMap<String, MultipartFile> fileMap = ((MultipartHttpServletRequest)httpServletRequest).getMultiFileMap();
        Iterator<String> fileNameIterator = fileMap.keySet().iterator();
        String fileName = "";
        System.out.println("=================== " + fileBean.getFile() );
        while(fileNameIterator.hasNext()) {
        	fileName = fileNameIterator.next();
        }
        System.out.println("======== file name is " + fileName);
		MultipartFile file =  ((MultipartHttpServletRequest)httpServletRequest).getFile(fileName);
		
		String mime_type = file.getContentType();
		
		System.out.println("======================= file type is " + mime_type);
		
	       ByteArrayInputStream bis = new ByteArrayInputStream(file.getBytes());
	       Workbook exWorkBook;
	        try {
	            if (file.getOriginalFilename().endsWith("xls") ||
	            		file.getOriginalFilename().endsWith("xlsx") 	) {
	                exWorkBook = WorkbookFactory.create(bis);
	                readExcelAndParseToProducts(exWorkBook, file.getOriginalFilename());
	            } else {
	                throw new IllegalArgumentException("Received file does not have a standard excel extension.");
	            }
//	            Sheet sheet = exWorkBook.getSheetAt(0);
//	            for (Row row : sheet) {
//	               if (row.getRowNum() == 0) {
//	                  Iterator<Cell> cellIterator = row.cellIterator();
//	                  while (cellIterator.hasNext()) {
//	                      Cell cell = cellIterator.next();
//	                      //go from cell to cell and do create sql based on the content
//	                      System.out.println(cell.getStringCellValue());
//	                  }
//	               }
//	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        bis.close();
	        return "redirect:/products";
	}
	
	//import products from excel file.
	@Transactional
	private void readExcelAndParseToProducts(Workbook workBook, String fileName) throws Exception{
		String groupName = fileName.substring(0,fileName.lastIndexOf("."));
		ProductGroup pg = productGroupService.findByGroupCode(groupName);
		if(pg == null){
			pg = new ProductGroup();
			pg.setGroupCode(groupName);
			pg.setGroupName(groupName);
			pg = productGroupService.save(pg);
		}
		for(int i=0; i<workBook.getNumberOfSheets(); i++){
			Sheet sheet = workBook.getSheetAt(i);
			String makerName = sheet.getSheetName(); // maker name
			
			//fileName is productGroup code.
			
			Maker maker = makerService.findByName(makerName);
			if(maker ==null){
				maker = new Maker();
				maker.setName(makerName);
				maker = makerService.save(maker);
			}
			List<ProductGroupMaker> pgms = productGroupMakerService.findByProductGroupAndMaker(pg, maker);
			if(pgms != null && pgms.size() >0){
				//do nothing
			}else{
				ProductGroupMaker pgm = new ProductGroupMaker();
				pgm.setCreatedDate(new Date().toString());
				pgm.setMaker(maker);
				pgm.setProductGroup(pg);
				productGroupMakerService.save(pgm);
			}
			for(Row row: sheet){
				if(row.getRowNum() >2){
					 //add new product
                    Product product = new Product();
                    product.setStartDate(new Date());
                    product.setMaker(maker);
                    product.setProductGroup(pg);
	                  Iterator<Cell> cellIterator = row.cellIterator();
	                  while (cellIterator.hasNext()) {
	                      Cell cell = cellIterator.next();
	                      if(cell.getColumnIndex() ==0){
	                    	 //productCode or model
	                    	  String value = cell.getStringCellValue();
	                    	  if(value == null || value.equalsIgnoreCase(""))
	                    		  break;
	                    	  product.setProductCode(value);
	                      }
	                      else if(cell.getColumnIndex() ==1){
	                    	  //desc in english
	                    	  
	                    	  String value = cell.getStringCellValue();
	                    	  if(value == null || value.equalsIgnoreCase(""))
	                    		  break;
	                    	  product.setProductName(value);
	                      }else if(cell.getColumnIndex() ==2){
	                    	  //desc in vietnamese
	                    	  
	                    	  String value = cell.getStringCellValue();
	                    	  if(value == null || value.equalsIgnoreCase(""))
	                    		  break;
	                    	  product.setProductNameVietnamese(value);
	                      }
	                      else if(cell.getColumnIndex() ==3){
	                    	  //specification
	                    	  String value = cell.getStringCellValue();
	                    	  product.setSpecification(value);
	                      }else if(cell.getColumnIndex() ==4){
	                    	  //unit
	                    	  String value = cell.getStringCellValue();
	                    	  product.setUnit(value);
	                      }else if(cell.getColumnIndex()==5){
	                    	  //unit price
//	                    	  DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
//	                    	  String cellValue = formatter.formatCellValue(cell); //Returns 
//	                    	  logger.info("============== cellValue = " + cellValue);
	                    	  try{
		                    	  Double value = (cell.getNumericCellValue());
		                    	  Float floatValue = Float.valueOf(value.toString());
		                    	  product.setMat_w_o_Tax_VND(floatValue);
	                    	  }catch(Exception e){
	                    		  //handle exception later.
	                    	  }
	                      }else if(cell.getColumnIndex() ==6){
	                    	  //labour (usd)
	                    	  Double value = (cell.getNumericCellValue());
	                    	  Float floatValue = Float.valueOf(value.toString());
	                    	  product.setLabour(floatValue);
	                      }
	                      else
	                    	  break;
	                      
	                  }
	                  if(product.getProductCode() != null){
	                      product = productService.save(product);
	                      ProductJson json = new ProductJson();
	                      json.setProductID(product.getProductID());
	                      json.setStartDate(new Date());
	                      
	                      json.setMat_w_o_Tax_USD(product.getMat_w_o_Tax_USD());
	                      json.setMat_w_o_Tax_VND(product.getMat_w_o_Tax_VND());
	                      json.setLabour(product.getLabour());
	                      
//	                      saveLabourPrice(json, product);
	                  }
				}
			}
		}
		
	}
	
	public boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Float.parseFloat(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
}
