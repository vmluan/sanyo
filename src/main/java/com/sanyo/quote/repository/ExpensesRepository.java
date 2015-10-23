package com.sanyo.quote.repository;

import com.sanyo.quote.domain.Expenses;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by User on 10/21/2015.
 */
public interface ExpensesRepository extends PagingAndSortingRepository<Expenses, Integer> {

}
