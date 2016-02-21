package com.sanyo.quote.helper;

import com.sanyo.quote.domain.Project;
import com.sanyo.quote.service.ExpensesService;

/*
 * author : luan
 * this class contains methods to calculate complex queries and return a single result to display in excel report
 *  */
public class ReportDataHelper {
	private ExpensesService expensesService;
	
	public ReportDataHelper(ExpensesService expensesService){
		this.expensesService = expensesService;
	}
	public float getSiteExpensesSummary(Project project){
		float result = 0f;
		//Site Expenses = 'M&E Expenses'!H23 - 'M&E Expenses'!G44 - 'M&E Expenses'!G57
		float h23 = expensesService.getSiteExpensesH23(project);
		float g44 = expensesService.getSiteExpensesG44(project);
		float g57 = expensesService.getSiteExpensesG57(project);
		result = h23 - g44 - g57;
		return result;
	}
}
