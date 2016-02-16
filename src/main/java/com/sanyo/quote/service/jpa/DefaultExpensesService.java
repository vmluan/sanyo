package com.sanyo.quote.service.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.ExpenseElements;
import com.sanyo.quote.domain.Expenses;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.helper.ConnectionUtil;
import com.sanyo.quote.repository.ExpensesRepository;
import com.sanyo.quote.service.ExpensesService;

/**
 * Created by User on 10/24/2015.
 */
@Service("expensesService")
@Repository
@Transactional
public class DefaultExpensesService implements ExpensesService {

	@Autowired
	ExpensesRepository expensesRepository;
	private static final String QUERY_H23 = "SELECT sum(e.rate) "
			+ "FROM Expenses e JOIN e.expenseElement el "
			+ "WHERE e.project =? AND (el.expenseElementID BETWEEN 13 AND 29)"
			+ "group by e.project";
	private static final String QUERY_H6 = "SELECT sum(e.rate) "
			+ "FROM Expenses e JOIN e.expenseElement el "
			+ "WHERE e.project =? AND (el.expenseElementID BETWEEN 1 AND 3)"
			+ "group by e.project";
	private static final String QUERY_H11 = "SELECT sum(e.rate) "
			+ "FROM Expenses e JOIN e.expenseElement el "
			+ "WHERE e.project =? AND (el.expenseElementID BETWEEN 4 AND 12)"
			+ "group by e.project";
	private static final String QUERY_G44 = "SELECT e.rate "
			+ "FROM Expenses e JOIN e.expenseElement el "
			+ "WHERE e.project =? AND (el.expenseElementID = 30)";
	private static final String QUERY_G57 = "SELECT e.rate "
			+ "FROM Expenses e JOIN e.expenseElement el "
			+ "WHERE e.project =? AND (el.expenseElementID = 38)";
	private static final String QUERY_G13 = "SELECT e.rate "
			+ "FROM Expenses e JOIN e.expenseElement el "
			+ "WHERE e.project =? AND (el.expenseElementID = 4)";
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
	public Expenses findByProjectAndElement(Project project, ExpenseElements expenseElements) {
		List<Expenses> expenses = expensesRepository.findByProjectAndElement(project, expenseElements);
		return expenses.size() != 0 ? expenses.get(0) : null;
	}

	@Transactional(readOnly = true)
	public List<Expenses> findByProject(Project project) {
		List<Expenses> expenses = expensesRepository.findByProject(project);
		return expenses;
	}

	@Override
	public Float getSiteExpensesH23(Project project) {
		Float result = 0f;
		ConnectionUtil con = null;
		try {
			con = new ConnectionUtil();
			String queryString ="SELECT sum(e.rate) FROM Expenses e JOIN e.expenseElement el WHERE e.project =? AND (el.expenseElementID BETWEEN 13 AND 29)"
					+ "group by e.project";
			//as Sum in Expenses is the keywork in mysql . Should use another name like sumOf...
			
			Query query = con.getQueryObj(queryString);
			query.setParameter(1, project);
			List<Object[]> list = query.getResultList();
			for(Object[] objs : list){
				if(objs != null){
					float sum = Float.valueOf(objs[0].toString());
					result += sum;
					System.out.println("=========== sum = " + sum);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
		return result;
	}

	@Override
	public Float getSiteExpensesG44(Project project) {
		Float result = 0f;
		ConnectionUtil con = null;
		try {
			con = new ConnectionUtil();
			String queryString ="SELECT sum(e.rate) FROM Expenses e JOIN e.expenseElement el WHERE e.project =? AND (el.expenseElementID BETWEEN 13 AND 29)"
					+ "group by e.project";
			//as Sum in Expenses is the keywork in mysql . Should use another name like sumOf...
			
			Query query = con.getQueryObj(QUERY_G44);
			query.setParameter(1, project);
			List<Object[]> list = query.getResultList();
			for(Object[] objs : list){
				if(objs != null){
					float sum = Float.valueOf(objs[0].toString());
					result += sum;
					System.out.println("=========== sum = " + sum);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
		return result;
	}

	@Override
	public Float getSiteExpensesG57(Project project) {
		Float result = 0f;
		ConnectionUtil con = null;
		try {
			con = new ConnectionUtil();
			
			Query query = con.getQueryObj(QUERY_G57);
			query.setParameter(1, project);
			List<Object[]> list = query.getResultList();
			for(Object[] objs : list){
				if(objs != null){
					float sum = Float.valueOf(objs[0].toString());
					result += sum;
					System.out.println("=========== sum = " + sum);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
		return result;
	}

	@Override
	public Float getSiteExpensesH6(Project project) {
		Float result = 0f;
		ConnectionUtil con = null;
		try {
			con = new ConnectionUtil();
			Query query = con.getQueryObj(QUERY_H6);
			query.setParameter(1, project);
			List<Object[]> list = query.getResultList();
			for(Object[] objs : list){
				if(objs != null){
					float sum = Float.valueOf(objs[0].toString());
					result += sum;
					System.out.println("=========== sum = " + sum);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
		return result;
	}

	@Override
	public Float getSiteExpensesG13(Project project) {
		Float result = 0f;
		ConnectionUtil con = null;
		try {
			con = new ConnectionUtil();
			Query query = con.getQueryObj(QUERY_G13);
			query.setParameter(1, project);
			List<Object[]> list = query.getResultList();
			for(Object[] objs : list){
				if(objs != null){
					float sum = Float.valueOf(objs[0].toString());
					result += sum;
					System.out.println("=========== sum = " + sum);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
		return result;
	}

	@Override
	public Float getSiteExpensesH11(Project project) {
		Float result = 0f;
		ConnectionUtil con = null;
		try {
			con = new ConnectionUtil();
			Query query = con.getQueryObj(QUERY_H11);
			query.setParameter(1, project);
			List<Object[]> list = query.getResultList();
			for(Object[] objs : list){
				if(objs != null){
					float sum = Float.valueOf(objs[0].toString());
					result += sum;
					System.out.println("=========== sum = " + sum);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
		return result;
	}
}
