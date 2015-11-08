package com.sanyo.quote.repository;

import com.sanyo.quote.domain.ExpenseElements;
import com.sanyo.quote.domain.Expenses;
import com.sanyo.quote.domain.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by User on 10/21/2015.
 */
public interface ExpensesRepository extends PagingAndSortingRepository<Expenses, Integer> {

    @Query("SELECT e FROM Expenses e WHERE e.project = :project and e.expenseElement = :expenseElement")
    public List<Expenses> findByProjectAndElement (@Param("project") Project project, @Param("expenseElement") ExpenseElements expenseElements);

    @Query("SELECT e FROM Expenses e WHERE e.project = :project")
    public List<Expenses> findByProject (@Param("project") Project project);
}
