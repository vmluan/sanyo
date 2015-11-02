package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.LabourPrice;
import com.sanyo.quote.domain.Price;
import com.sanyo.quote.repository.PriceRepository;
import com.sanyo.quote.service.PriceService;

/**
 * Created by User on 10/9/2015.
 */
@Service("priceService")
@Repository
@Transactional
public class DefaultPriceService implements PriceService {
    @Autowired
    private PriceRepository priceRepository;

    @Override
    public List<LabourPrice> findPriceByProductID(Integer productID) {
    	//return priceRepository.findPriceByProductID(productID);
    	return null;
    	}

	@Override
	public List<LabourPrice> findAll() {
		return Lists.newArrayList(priceRepository.findAll());
	}

	@Override
	public Price findById(Integer id) {
		return priceRepository.findOne(id);
	}

	@Override
	public Price save(LabourPrice price) {
		return priceRepository.save(price);
	}

	@Override
	public Page<LabourPrice> findAllByPage(Pageable pageable) {
		return priceRepository.findAll(pageable);
	}

	@Override
	public void delete(Integer id) {
		priceRepository.delete(id);
	}

	@Override
	public void delete(LabourPrice price) {
		priceRepository.delete(price);
	}
}
