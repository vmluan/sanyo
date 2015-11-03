
package com.sanyo.quote.service.jpa;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanyo.quote.domain.Category;
import com.sanyo.quote.domain.LabourPrice;
import com.sanyo.quote.domain.Product;
import com.sanyo.quote.repository.ProductRepository;
import com.sanyo.quote.service.ProductService;

@Service("productService")
@Repository
@Transactional
public class DefaultProductService implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly=true)
	public List<Product> findAll() {
		//return Lists.newArrayList(productRepository.findAll());
		return productRepository.findValidProduct();
	}

	@Transactional(readOnly=true)
	public Product findById(Integer id) {
		Product product =  productRepository.findOne(id);
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		if(product.getStartDate() != null) 
			product.setStartDateString(format.format(product.getStartDate()));
		if(product.getEndDate() != null)
			product.setEndDateString(format.format(product.getEndDate()));
		return product;
	}

	public Product save(Product TH_Product) {
		return productRepository.save(TH_Product);
	}

	@Transactional(readOnly=true)
	public Page<Product> findAllByPage(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public Product findByName(String productName) {
		return productRepository.findProductByName(productName).get(0);
	}

	@Override
	public Product findByCode(String productCode) {return productRepository.findProductByCode(productCode);}

	@Override
	public void delete(Integer id) {
		productRepository.delete(id);
		
	}

	@Override
	public void delte(Product product) {
		productRepository.delete(product);
	}

	@Override
	public void deleteProduct(Product product) {
		// becuase product is refered by encounter, it can not be deleted. It will violate foreign key violation.
		// we will keep the product in the database, but mark it as isDeleted
		product.setDeltedDate(new Date());
		product.setDeleted(true);
		//change name to avoid conflicting product name when user want to add that name again
		String temp = String.valueOf(new Date().getTime());
		product.setProductName(product.getProductName() + "_" + temp);
		productRepository.save(product);
	}

	@Override
	public List<LabourPrice> findLabourPrices(Integer id) {
		return productRepository.findLabourPrices(id);
	}

	@Override
	public List<Category> findCategories(Integer id) {
		return productRepository.findCategories(id);
	}
	
	
	
}
