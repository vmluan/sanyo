package com.sanyo.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.Condition2;
import com.sanyo.quote.domain.ExpenseElements;
import com.sanyo.quote.domain.Expenses;
import com.sanyo.quote.domain.Project;

public interface condition2service {
	List<Condition2> findAll();
	Condition2 save (Condition2 condition);
	//List<Condition2> findByProject (Condition2 condition);
	Condition2 findByProject (Project project);
}
