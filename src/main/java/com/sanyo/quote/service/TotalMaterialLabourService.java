package com.sanyo.quote.service;

import com.sanyo.quote.domain.TotalMaterialLabour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Chuong on 03/14/2016.
 */
public interface TotalMaterialLabourService {
    List<TotalMaterialLabour> findAll();

    TotalMaterialLabour findById(Integer id);

    TotalMaterialLabour save(TotalMaterialLabour TotalMaterialLabour);

    Page<TotalMaterialLabour> findAllByPage(Pageable pageable);

}
