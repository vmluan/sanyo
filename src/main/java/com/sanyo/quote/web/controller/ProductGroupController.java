package com.sanyo.quote.web.controller;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanyo.quote.domain.Maker;
import com.sanyo.quote.domain.MakerProject;
import com.sanyo.quote.domain.Product;
import com.sanyo.quote.domain.ProductGroup;
import com.sanyo.quote.domain.ProductGroupMaker;
import com.sanyo.quote.domain.Region;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.MakerProjectService;
import com.sanyo.quote.service.ProductGroupService;
import com.sanyo.quote.service.ProductService;
import com.sanyo.quote.service.RegionService;

@Controller
@RequestMapping(value = "/productgroups")
public class ProductGroupController {
	final Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	private ProductGroupService productGroupService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private MakerProjectService makerProjectService;
	
	@RequestMapping(value = "/getproductGroupJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getMakersJson(@RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		List<ProductGroup> productGroups = productGroupService.findAll();
		String result = Utilities.jSonSerialization(productGroups);
		return result;
	}
	
	@RequestMapping(value = "/getProductsOfGroupjson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getProductsOfGroupjson(@RequestParam(value="productGroupId", required=false) Integer productGroupId,
			 @RequestParam(value="regionId", required=false) Integer regionId,
			 @RequestParam(value="filterscount", required=false) String filterscount
			, @RequestParam(value="groupscount", required=false) String groupscount
			, @RequestParam(value="pagenum", required=false) Integer pagenum
			, @RequestParam(value="pagesize", required=false) Integer pagesize
			, @RequestParam(value="recordstartindex", required=false) Integer recordstartindex
			, @RequestParam(value="recordendindex", required=false) Integer recordendindex
			, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		if(regionId != null && productGroupId != null){
			TreeMap<Integer, Product> treeMap = new TreeMap<Integer, Product>();
			Region region = regionService.findById(regionId);
			ProductGroup productGroup = productGroupService.findById(productGroupId);
			List<MakerProject> makerProjects = makerProjectService.findByProjectAndCategory(region.getLocation().getProject(), region.getCategory());

			//next is find distinct productgroup and maker
			//next is find product of above group and maker.
			for(MakerProject makerProject: makerProjects){
				ProductGroupMaker pgm = makerProject.getProductGroupMaker();
				Maker maker = pgm.getMaker();
				List<Product> products = productService.findByProductGroupAndMaker(productGroup, maker);
				if(products != null && products.size() >0){
					for(Product product : products){
						treeMap.put(product.getProductID(),product);
					}
				}
				
			}
			return Utilities.jSonSerialization(treeMap.values());
		}else
			return "[]";
		
//		if(productGroupId == null){ //load all products
//			List<Product> products =  productService.findAll();
//			return Utilities.jSonSerialization(products);
//		}else{
//			ProductGroup pg = productGroupService.findByIdAndFetchProductsEagerly(Integer.valueOf(productGroupId));
//			Set<Product> products = pg.getProducts();
//			return Utilities.jSonSerialization(products);
//		}
	}
}
