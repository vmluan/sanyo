package com.sanyo.quote.service;

import com.sanyo.quote.domain.ExpenseElements;
import com.sanyo.quote.domain.Expenses;
import com.sanyo.quote.domain.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by User on 10/24/2015.
 */
public interface ExpensesService {
    List<Expenses> findAll();

    Expenses findById (Integer Id);

    Expenses findByProjectAndElement (Project project, ExpenseElements expenseElements);

    List<Expenses> findByProject (Project project);

    Expenses save (Expenses expense);

    Page<Expenses> findAllByPage(Pageable pageable);
    
    public Float getSiteExpensesH23(Project project);
    public Float getSiteExpensesG44(Project project);
    public Float getSiteExpensesG57(Project project);
    public Float getSiteExpensesH6(Project project);
    public Float getSiteExpensesG13(Project project);
    public Float getSiteExpensesH11(Project project);

}
