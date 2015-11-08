package com.sanyo.quote.service.jpa;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.ExpenseElements;
import com.sanyo.quote.domain.Expenses;
import com.sanyo.quote.domain.ExpensesJson;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.repository.ExpensesRepository;
import com.sanyo.quote.service.ExpensesService;
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
@Service("expensesService")
@Repository
@Transactional
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

    @Transactional(readOnly = true)
    public Expenses findByProjectAndElement (Project project, ExpenseElements expenseElements) {
        List<Expenses> expenses = expensesRepository.findByProjectAndElement(project, expenseElements);
        return expenses.size()!=0?expenses.get(0):null;
    }

    @Transactional(readOnly = true)
    public List<Expenses> findByProject (Project project) {
        List<Expenses> expenses = expensesRepository.findByProject(project);
        return expenses;
    }
}
