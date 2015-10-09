package com.sanyo.quote.service;

import com.sanyo.quote.domain.Price;

import java.util.List;

/**
 * Created by User on 10/9/2015.
 */
public interface PriceService {
    List<Price> findPriceByProductID(Integer productID);
}
