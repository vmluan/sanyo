package com.sanyo.quote.web.controller;

import com.sanyo.quote.domain.ExpenseElements;
import com.sanyo.quote.domain.ExpensesJson;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by User on 10/11/2015.
 */

@Controller
@RequestMapping(value = "/expenses")
public class ExpensesController extends CommonController{

    private List<ExpenseElements> allExpenseElement;

    @RequestMapping(params = "form",method = RequestMethod.GET)
    public String getExpenses(Model uiModel) {
        ExpensesJson expensesJson = new ExpensesJson();
        uiModel.addAttribute("expensesJson",expensesJson);
        setHeader(uiModel, "Update expenses", "");
        setUser(uiModel);
        return "quotation/expenses";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveExpenses(Model uiModel) {

        return "quotation/expenses";
    }

}
