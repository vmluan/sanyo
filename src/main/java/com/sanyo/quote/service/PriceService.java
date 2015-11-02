package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.LabourPrice;
import com.sanyo.quote.domain.Price;

/**
 * Created by User on 10/9/2015.
 */
public interface PriceService {
    List<LabourPrice> findPriceByProductID(Integer productID);
	List<LabourPrice> findAll();
	
	Price findById(Integer id);
	
	Price save(LabourPrice price);
	
	Page<LabourPrice> findAllByPage(Pageable pageable);
	void delete(Integer id);
	void delete(LabourPrice price);
}
