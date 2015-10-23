package com.sanyo.quote.service;

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

    Expenses save (Expenses expense);

    Page<Expenses> findAllByPage(Pageable pageable);

}
