package com.sanyo.quote.web.controller;

import com.sanyo.quote.domain.ExpenseElements;
import com.sanyo.quote.domain.Expenses;
import com.sanyo.quote.domain.ExpensesJson;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.service.ExpenseElementsService;
import com.sanyo.quote.service.ExpensesService;
import com.sanyo.quote.service.ProjectService;
import com.sanyo.quote.web.form.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;


/**
 * Created by User on 10/11/2015.
 */

@Controller
@RequestMapping(value = "/expenses")
public class ExpensesController extends CommonController{
    final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    MessageSource messageSource;

    @Autowired
    ExpensesService expensesService;

    @Autowired
    ExpenseElementsService expenseElementsService;

    @Autowired
    ProjectService projectService;

    private List<ExpenseElements> allExpenseElement;

    @RequestMapping(value = "/{id}/expenses",params = "form",method = RequestMethod.GET)
    public String createExpenses(@PathVariable("id") String projectId,Model uiModel) {
        ExpensesJson expensesJson = new ExpensesJson();
        uiModel.addAttribute("expensesJson",expensesJson);
        uiModel.addAttribute("projectId",projectId);
        setHeader(uiModel, "Update expenses", "");
        setUser(uiModel);
        return "quotation/expenses/create";
    }

    @RequestMapping(value = "/{id}/expenses", params = "form", method = RequestMethod.POST)
    public String saveLocation(@RequestBody final ExpensesJson expensesJson, @PathVariable Integer id, Model uiModel,
                               HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale, BindingResult bindingResult){
        logger.info("Saving new Location");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("location_save_fail", new Object[]{}, locale)));
            uiModel.addAttribute("expensesJson", expensesJson);
            return "/quotation/expenses/create";
        }

        List<ExpenseElements> expenseElementses = expenseElementsService.findAll();
        java.lang.reflect.Method method;
        for (ExpenseElements expenseElement : expenseElementses){
           Expenses expense = new Expenses();
            expense.setExpenseElement(expenseElement);
            expense.setProject(projectService.findById(id));
            expense.setOrder(expenseElement.getDefaultOrder());

            //think about this more
            int elementID = expenseElement.getExpenseElementID();
            if (elementID == 1)
                expense.setQuantity(expensesJson.getExpenseElement_1());
            else if (elementID == 2)
                expense.setQuantity(expensesJson.getExpenseElement_2());
            else if (elementID == 3)
                expense.setQuantity(expensesJson.getExpenseElement_3());
            else if (elementID == 4)
                expense.setQuantity(expensesJson.getExpenseElement_4());
            else if (elementID == 5)
                expense.setQuantity(expensesJson.getExpenseElement_5());
            else if (elementID == 6)
                expense.setQuantity(expensesJson.getExpenseElement_6());
            else if (elementID == 7)
                expense.setQuantity(expensesJson.getExpenseElement_7());
            else if (elementID == 8)
                expense.setQuantity(expensesJson.getExpenseElement_8());
            else if (elementID == 9)
                expense.setQuantity(expensesJson.getExpenseElement_9());
            else if (elementID == 10)
                expense.setQuantity(expensesJson.getExpenseElement_10());
            else if (elementID == 11)
                expense.setQuantity(expensesJson.getExpenseElement_11());
            else if (elementID == 12)
                expense.setQuantity(expensesJson.getExpenseElement_12());
            else if (elementID == 13)
                expense.setQuantity(expensesJson.getExpenseElement_13());
            else if (elementID == 14)
                expense.setQuantity(expensesJson.getExpenseElement_14());
            else if (elementID == 15)
                expense.setQuantity(expensesJson.getExpenseElement_15());
            else if (elementID == 16)
                expense.setQuantity(expensesJson.getExpenseElement_16());
            else if (elementID == 17)
                expense.setQuantity(expensesJson.getExpenseElement_17());
            else if (elementID == 18)
                expense.setQuantity(expensesJson.getExpenseElement_18());
            else if (elementID == 19)
                expense.setQuantity(expensesJson.getExpenseElement_19());
            else if (elementID == 20)
                expense.setQuantity(expensesJson.getExpenseElement_20());
            else if (elementID == 21)
                expense.setQuantity(expensesJson.getExpenseElement_21());
            else if (elementID == 22)
                expense.setQuantity(expensesJson.getExpenseElement_22());
            else if (elementID == 23)
                expense.setQuantity(expensesJson.getExpenseElement_23());
            else if (elementID == 24)
                expense.setQuantity(expensesJson.getExpenseElement_24());
            else if (elementID == 25)
                expense.setQuantity(expensesJson.getExpenseElement_25());
            else if (elementID == 26)
                expense.setQuantity(expensesJson.getExpenseElement_26());
            else if (elementID == 27)
                expense.setQuantity(expensesJson.getExpenseElement_27());
            else if (elementID == 28)
                expense.setQuantity(expensesJson.getExpenseElement_28());
            else if (elementID == 29)
                expense.setQuantity(expensesJson.getExpenseElement_29());
            else if (elementID == 30)
                expense.setQuantity(expensesJson.getExpenseElement_30());
            else if (elementID == 31)
                expense.setQuantity(expensesJson.getExpenseElement_31());
            else if (elementID == 32)
                expense.setQuantity(expensesJson.getExpenseElement_32());
            else if (elementID == 33)
                expense.setQuantity(expensesJson.getExpenseElement_33());
            else if (elementID == 34)
                expense.setQuantity(expensesJson.getExpenseElement_34());
            else if (elementID == 35)
                expense.setQuantity(expensesJson.getExpenseElement_35());
            else if (elementID == 36)
                expense.setQuantity(expensesJson.getExpenseElement_36());
            else if (elementID == 37)
                expense.setQuantity(expensesJson.getExpenseElement_37());
            else if (elementID == 38)
                expense.setQuantity(expensesJson.getExpenseElement_38());
            else if (elementID == 39)
                expense.setQuantity(expensesJson.getExpenseElement_39());
            else if (elementID == 40)
                expense.setQuantity(expensesJson.getExpenseElement_40());
            else if (elementID == 41)
                expense.setQuantity(expensesJson.getExpenseElement_41());
            else if (elementID == 42)
                expense.setQuantity(expensesJson.getExpenseElement_42());
            else if (elementID == 43)
                expense.setQuantity(expensesJson.getExpenseElement_43());
            else if (elementID == 44)
                expense.setQuantity(expensesJson.getExpenseElement_44());
            else if (elementID == 45)
                expense.setQuantity(expensesJson.getExpenseElement_45());
            else if (elementID == 46)
                expense.setQuantity(expensesJson.getExpenseElement_46());
            else if (elementID == 47)
                expense.setQuantity(expensesJson.getExpenseElement_47());
            else if (elementID == 48)
                expense.setQuantity(expensesJson.getExpenseElement_48());
            else if (elementID == 49)
                expense.setQuantity(expensesJson.getExpenseElement_49());
            else if (elementID == 50)
                expense.setQuantity(expensesJson.getExpenseElement_50());
            else if (elementID == 51)
                expense.setQuantity(expensesJson.getExpenseElement_51());
            else if (elementID == 52)
                expense.setQuantity(expensesJson.getExpenseElement_52());
            else if (elementID == 53)
                expense.setQuantity(expensesJson.getExpenseElement_53());
            else if (elementID == 54)
                expense.setQuantity(expensesJson.getExpenseElement_54());
            else if (elementID == 55)
                expense.setQuantity(expensesJson.getExpenseElement_55());
            else if (elementID == 56)
                expense.setQuantity(expensesJson.getExpenseElement_56());
            else if (elementID == 57)
                expense.setQuantity(expensesJson.getExpenseElement_57());
            else if (elementID == 58)
                expense.setQuantity(expensesJson.getExpenseElement_58());
            else if (elementID == 59)
                expense.setQuantity(expensesJson.getExpenseElement_59());
            else if (elementID == 60)
                expense.setQuantity(expensesJson.getExpenseElement_60());
            else
                expense.setQuantity(expensesJson.getExpenseElement_61());


            expensesService.save(expense);
        }



        return "quotation/expenses";
    }

}
