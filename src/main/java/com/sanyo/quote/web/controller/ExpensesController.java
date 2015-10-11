package com.sanyo.quote.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by User on 10/11/2015.
 */

@Controller
@RequestMapping(value = "/expenses")
public class ExpensesController {

    @RequestMapping(method = RequestMethod.GET)
    public String getExpenses(Model uiModel) {
        return "quotation/expenses";
    }

}
