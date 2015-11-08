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
    public String createExpenses(@PathVariable("id") Integer projectId,Model uiModel) {
        ExpensesJson expensesJson = new ExpensesJson();
        List<Expenses> expenseses = expensesService.findByProject(projectService.findById(projectId));
        if (expenseses.size()>0 && expenseses.size()>=61) {
            expensesJson.setExpenseElement_1(expenseses.get(0).getQuantity());
            expensesJson.setExpenseElement_2(expenseses.get(1).getQuantity());
            expensesJson.setExpenseElement_3(expenseses.get(2).getQuantity());
            expensesJson.setExpenseElement_4(expenseses.get(3).getQuantity());
            expensesJson.setExpenseElement_5(expenseses.get(4).getQuantity());
            expensesJson.setExpenseElement_6(expenseses.get(5).getQuantity());
            expensesJson.setExpenseElement_7(expenseses.get(6).getQuantity());
            expensesJson.setExpenseElement_8(expenseses.get(7).getQuantity());
            expensesJson.setExpenseElement_9(expenseses.get(8).getQuantity());
            expensesJson.setExpenseElement_10(expenseses.get(9).getQuantity());
            expensesJson.setExpenseElement_11(expenseses.get(10).getQuantity());
            expensesJson.setExpenseElement_12(expenseses.get(11).getQuantity());
            expensesJson.setExpenseElement_13(expenseses.get(12).getQuantity());
            expensesJson.setExpenseElement_14(expenseses.get(13).getQuantity());
            expensesJson.setExpenseElement_15(expenseses.get(14).getQuantity());
            expensesJson.setExpenseElement_16(expenseses.get(15).getQuantity());
            expensesJson.setExpenseElement_17(expenseses.get(16).getQuantity());
            expensesJson.setExpenseElement_18(expenseses.get(17).getQuantity());
            expensesJson.setExpenseElement_19(expenseses.get(18).getQuantity());
            expensesJson.setExpenseElement_20(expenseses.get(19).getQuantity());
            expensesJson.setExpenseElement_21(expenseses.get(20).getQuantity());
            expensesJson.setExpenseElement_22(expenseses.get(21).getQuantity());
            expensesJson.setExpenseElement_23(expenseses.get(22).getQuantity());
            expensesJson.setExpenseElement_24(expenseses.get(23).getQuantity());
            expensesJson.setExpenseElement_25(expenseses.get(24).getQuantity());
            expensesJson.setExpenseElement_26(expenseses.get(25).getQuantity());
            expensesJson.setExpenseElement_27(expenseses.get(26).getQuantity());
            expensesJson.setExpenseElement_28(expenseses.get(27).getQuantity());
            expensesJson.setExpenseElement_29(expenseses.get(28).getQuantity());
            expensesJson.setExpenseElement_30(expenseses.get(29).getQuantity());
            expensesJson.setExpenseElement_31(expenseses.get(30).getQuantity());
            expensesJson.setExpenseElement_32(expenseses.get(31).getQuantity());
            expensesJson.setExpenseElement_33(expenseses.get(32).getQuantity());
            expensesJson.setExpenseElement_34(expenseses.get(33).getQuantity());
            expensesJson.setExpenseElement_35(expenseses.get(34).getQuantity());
            expensesJson.setExpenseElement_36(expenseses.get(35).getQuantity());
            expensesJson.setExpenseElement_37(expenseses.get(36).getQuantity());
            expensesJson.setExpenseElement_38(expenseses.get(37).getQuantity());
            expensesJson.setExpenseElement_39(expenseses.get(38).getQuantity());
            expensesJson.setExpenseElement_40(expenseses.get(39).getQuantity());
            expensesJson.setExpenseElement_41(expenseses.get(40).getQuantity());
            expensesJson.setExpenseElement_42(expenseses.get(41).getQuantity());
            expensesJson.setExpenseElement_43(expenseses.get(42).getQuantity());
            expensesJson.setExpenseElement_44(expenseses.get(43).getQuantity());
            expensesJson.setExpenseElement_45(expenseses.get(44).getQuantity());
            expensesJson.setExpenseElement_46(expenseses.get(45).getQuantity());
            expensesJson.setExpenseElement_47(expenseses.get(46).getQuantity());
            expensesJson.setExpenseElement_48(expenseses.get(47).getQuantity());
            expensesJson.setExpenseElement_49(expenseses.get(48).getQuantity());
            expensesJson.setExpenseElement_50(expenseses.get(49).getQuantity());
            expensesJson.setExpenseElement_51(expenseses.get(50).getQuantity());
            expensesJson.setExpenseElement_52(expenseses.get(51).getQuantity());
            expensesJson.setExpenseElement_53(expenseses.get(52).getQuantity());
            expensesJson.setExpenseElement_54(expenseses.get(53).getQuantity());
            expensesJson.setExpenseElement_55(expenseses.get(54).getQuantity());
            expensesJson.setExpenseElement_56(expenseses.get(55).getQuantity());
            expensesJson.setExpenseElement_57(expenseses.get(56).getQuantity());
            expensesJson.setExpenseElement_58(expenseses.get(57).getQuantity());
            expensesJson.setExpenseElement_59(expenseses.get(58).getQuantity());
            expensesJson.setExpenseElement_60(expenseses.get(59).getQuantity());
            expensesJson.setExpenseElement_61(expenseses.get(60).getQuantity());

            expensesJson.setExpenseElementQuantity_1(expenseses.get(1).getDuration());
        }
        uiModel.addAttribute("expensesJson", expensesJson);
        uiModel.addAttribute("projectId",projectId);
        setHeader(uiModel, "Update expenses", "");
        setUser(uiModel);
        return "quotation/expenses/create";
    }

    @RequestMapping(value = "/{id}/saveExpenselist", params = "form", method = RequestMethod.POST)
    public String saveExpenses(@RequestBody final ExpensesJson expensesJson, @PathVariable Integer id, Model uiModel,
                               HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Locale locale){
        logger.info("Saving expense list");
//        if (bindingResult.hasErrors()) {
//            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("expense_save_fail", new Object[]{}, locale)));
//            uiModel.addAttribute("expensesJson", expensesJson);
//            return "/quotation/expenses/create";
//        }

        List<ExpenseElements> expenseElementses = expenseElementsService.findAll();
        Project project = projectService.findById(id);
        for (ExpenseElements expenseElement : expenseElementses){
            Expenses expense = expensesService.findByProjectAndElement(project, expenseElement);
            if (expense != null)
            {
                //do nothing
            }
            else {
                expense = new Expenses();
                expense.setProject(project);
                expense.setExpenseElement(expenseElement);

                expense.setOrder(expenseElement.getDefaultOrder());
            }

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
