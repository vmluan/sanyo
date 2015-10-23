package com.sanyo.quote.repository;

import com.sanyo.quote.domain.ExpenseElements;
import com.sanyo.quote.domain.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by User on 10/21/2015.
 */
public interface ExpenseElementsRepository extends PagingAndSortingRepository<ExpenseElements, Integer> {

}
