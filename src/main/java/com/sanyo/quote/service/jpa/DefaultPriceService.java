package com.sanyo.quote.service.jpa;

import com.sanyo.quote.domain.Price;
import com.sanyo.quote.repository.PriceRepository;
import com.sanyo.quote.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by User on 10/9/2015.
 */
public class DefaultPriceService implements PriceService {
    @Autowired
    private PriceRepository priceRepository;

    @Override
    public List<Price> findPriceByProductID(Integer productID) {return priceRepository.findPriceByProductID(productID);}
}
