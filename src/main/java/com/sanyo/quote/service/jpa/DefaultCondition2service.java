package com.sanyo.quote.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.Condition2;
import com.sanyo.quote.domain.Expenses;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.repository.ExpensesRepository;
import com.sanyo.quote.repository.condition2Repository;
import com.sanyo.quote.service.condition2service;

@Service("condition2service")
@Repository
@Transactional
public class DefaultCondition2service implements condition2service{
	
	@Autowired
    condition2Repository condition2;
	@Transactional(readOnly = true)
    public List<Condition2> findAll() {
        return Lists.newArrayList(condition2.findAll());
    }
	@Override
    public Condition2 save(Condition2 condition) {
        return condition2.save(condition);
    }
	@Transactional(readOnly = true)
    public Condition2 findByProject (Project project) {
        Condition2 condition = condition2.findByProject(project);
        return condition;
    }
	/*
	@Override
	public Condition2 update(Condition2 condition) {
        return condition2.update(condition.getProject(), condition.getCheckboxs(), condition.getContents());
    }*/
	/*@Transactional(readOnly=true)
	public List<Condition2> findByProject(Condition2 condition) {
        return condition2.findByProject(condition.getProject());
    }*/
}
