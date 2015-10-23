package com.sanyo.quote.service.jpa;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.Expenses;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.repository.ExpensesRepository;
import com.sanyo.quote.service.ExpensesService;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by User on 10/24/2015.
 */
public class DefaultExpensesService implements ExpensesService {

    @Autowired
    ExpensesRepository expensesRepository;

    @Transactional(readOnly = true)
    public List<Expenses> findAll() {
        return Lists.newArrayList(expensesRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Expenses findById(Integer Id) {
        return expensesRepository.findOne(Id);
    }

    @Override
    public Expenses save(Expenses expense) {
        return expensesRepository.save(expense);
    }

    @Transactional
    public Page<Expenses> findAllByPage(Pageable pageable) {
        return expensesRepository.findAll(pageable);
    }

}
