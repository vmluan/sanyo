package com.sanyo.quote.service;

import com.sanyo.quote.domain.ExpenseElements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by User on 10/24/2015.
 */
public interface ExpenseElementsService {
    List<ExpenseElements> findAll();

    ExpenseElements findById(Integer Id);

    ExpenseElements save (ExpenseElements expenseElement);

    Page<ExpenseElements> findAllByPage(Pageable pageable);

}
