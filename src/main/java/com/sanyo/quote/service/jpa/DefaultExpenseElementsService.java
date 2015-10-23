package com.sanyo.quote.service.jpa;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.ExpenseElements;
import com.sanyo.quote.repository.ExpenseElementsRepository;
import com.sanyo.quote.service.ExpenseElementsService;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by User on 10/24/2015.
 */
@Service("expenseElementsService")
@Repository
@Transactional
public class DefaultExpenseElementsService implements ExpenseElementsService {
    @Autowired
    ExpenseElementsRepository expenseElementsRepository;

    @Transactional(readOnly=true)
    public List<ExpenseElements> findAll() {
        return Lists.newArrayList(expenseElementsRepository.findAll());
    }

    @Transactional(readOnly=true)
    public ExpenseElements findById(Integer id) {
        return expenseElementsRepository.findOne(id);
    }

    @Override
    public ExpenseElements save(ExpenseElements group) {
        return expenseElementsRepository.save(group);
    }

    @Transactional(readOnly=true)
    public Page<ExpenseElements> findAllByPage(Pageable pageable) {
        return expenseElementsRepository.findAll(pageable);
    }


}
