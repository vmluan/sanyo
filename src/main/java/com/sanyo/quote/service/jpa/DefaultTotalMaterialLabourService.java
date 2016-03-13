package com.sanyo.quote.service.jpa;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.TotalMaterialLabour;
import com.sanyo.quote.repository.ProductGroupRepository;
import com.sanyo.quote.repository.ProjectRepository;
import com.sanyo.quote.repository.TotalMaterialLabourRepository;
import com.sanyo.quote.service.TotalMaterialLabourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Chuong on 03/14/2016.
 */
@Service("totalMaterialLabourService")
@Repository
@Transactional
public class DefaultTotalMaterialLabourService implements TotalMaterialLabourService {

    @Autowired
    private TotalMaterialLabourRepository totalMaterialLabourRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProductGroupRepository productGroupRepository;

    @Override
    public List<TotalMaterialLabour> findAll() {
        return Lists.newArrayList(totalMaterialLabourRepository.findAll());
    }

    @Override
    public TotalMaterialLabour findById(Integer id) {
        return totalMaterialLabourRepository.findOne(id);
    }

    @Override
    public TotalMaterialLabour save(TotalMaterialLabour TotalMaterialLabour) {
        return totalMaterialLabourRepository.save(TotalMaterialLabour);
    }

    @Override
    public Page<TotalMaterialLabour> findAllByPage(Pageable pageable) {
        return totalMaterialLabourRepository.findAll(pageable);
    }

}
