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

            expensesJson.setExpenseElementDuration_1(expenseses.get(1).getDuration());
            expensesJson.setExpenseElementDuration_2(expenseses.get(2).getDuration());
            expensesJson.setExpenseElementDuration_3(expenseses.get(3).getDuration());
            expensesJson.setExpenseElementDuration_4(expenseses.get(4).getDuration());
            expensesJson.setExpenseElementDuration_5(expenseses.get(5).getDuration());
            expensesJson.setExpenseElementDuration_6(expenseses.get(6).getDuration());
            expensesJson.setExpenseElementDuration_7(expenseses.get(7).getDuration());
            expensesJson.setExpenseElementDuration_8(expenseses.get(8).getDuration());
            expensesJson.setExpenseElementDuration_9(expenseses.get(9).getDuration());
            expensesJson.setExpenseElementDuration_10(expenseses.get(10).getDuration());
            expensesJson.setExpenseElementDuration_11(expenseses.get(11).getDuration());
            expensesJson.setExpenseElementDuration_12(expenseses.get(12).getDuration());
            expensesJson.setExpenseElementDuration_13(expenseses.get(13).getDuration());
            expensesJson.setExpenseElementDuration_14(expenseses.get(14).getDuration());
            expensesJson.setExpenseElementDuration_15(expenseses.get(15).getDuration());
            expensesJson.setExpenseElementDuration_16(expenseses.get(16).getDuration());
            expensesJson.setExpenseElementDuration_17(expenseses.get(17).getDuration());
            expensesJson.setExpenseElementDuration_18(expenseses.get(18).getDuration());
            expensesJson.setExpenseElementDuration_19(expenseses.get(19).getDuration());
            expensesJson.setExpenseElementDuration_20(expenseses.get(20).getDuration());
            expensesJson.setExpenseElementDuration_21(expenseses.get(21).getDuration());
            expensesJson.setExpenseElementDuration_22(expenseses.get(22).getDuration());
            expensesJson.setExpenseElementDuration_23(expenseses.get(23).getDuration());
            expensesJson.setExpenseElementDuration_24(expenseses.get(24).getDuration());
            expensesJson.setExpenseElementDuration_25(expenseses.get(25).getDuration());
            expensesJson.setExpenseElementDuration_26(expenseses.get(26).getDuration());
            expensesJson.setExpenseElementDuration_27(expenseses.get(27).getDuration());
            expensesJson.setExpenseElementDuration_28(expenseses.get(28).getDuration());
            expensesJson.setExpenseElementDuration_29(expenseses.get(29).getDuration());
            expensesJson.setExpenseElementDuration_30(expenseses.get(30).getDuration());
            expensesJson.setExpenseElementDuration_31(expenseses.get(31).getDuration());
            expensesJson.setExpenseElementDuration_32(expenseses.get(32).getDuration());
            expensesJson.setExpenseElementDuration_33(expenseses.get(33).getDuration());
            expensesJson.setExpenseElementDuration_34(expenseses.get(34).getDuration());
            expensesJson.setExpenseElementDuration_35(expenseses.get(35).getDuration());
            expensesJson.setExpenseElementDuration_36(expenseses.get(36).getDuration());
            expensesJson.setExpenseElementDuration_37(expenseses.get(37).getDuration());
            expensesJson.setExpenseElementDuration_38(expenseses.get(38).getDuration());
            expensesJson.setExpenseElementDuration_39(expenseses.get(39).getDuration());
            expensesJson.setExpenseElementDuration_40(expenseses.get(40).getDuration());
            expensesJson.setExpenseElementDuration_41(expenseses.get(41).getDuration());
            expensesJson.setExpenseElementDuration_42(expenseses.get(42).getDuration());
            expensesJson.setExpenseElementDuration_43(expenseses.get(43).getDuration());
            expensesJson.setExpenseElementDuration_44(expenseses.get(44).getDuration());
            expensesJson.setExpenseElementDuration_45(expenseses.get(45).getDuration());
            expensesJson.setExpenseElementDuration_46(expenseses.get(46).getDuration());
            expensesJson.setExpenseElementDuration_47(expenseses.get(47).getDuration());
            expensesJson.setExpenseElementDuration_48(expenseses.get(48).getDuration());
            expensesJson.setExpenseElementDuration_49(expenseses.get(49).getDuration());
            expensesJson.setExpenseElementDuration_50(expenseses.get(50).getDuration());
            expensesJson.setExpenseElementDuration_51(expenseses.get(51).getDuration());
            expensesJson.setExpenseElementDuration_52(expenseses.get(52).getDuration());
            expensesJson.setExpenseElementDuration_53(expenseses.get(53).getDuration());
            expensesJson.setExpenseElementDuration_54(expenseses.get(54).getDuration());
            expensesJson.setExpenseElementDuration_55(expenseses.get(55).getDuration());
            expensesJson.setExpenseElementDuration_56(expenseses.get(56).getDuration());
            expensesJson.setExpenseElementDuration_57(expenseses.get(57).getDuration());
            expensesJson.setExpenseElementDuration_58(expenseses.get(58).getDuration());
            expensesJson.setExpenseElementDuration_59(expenseses.get(59).getDuration());
            expensesJson.setExpenseElementDuration_60(expenseses.get(60).getDuration());
            expensesJson.setExpenseElementDuration_61(expenseses.get(61).getDuration());

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
            if (elementID == 1) {
                expense.setQuantity(expensesJson.getExpenseElement_1());
                expense.setDuration(expensesJson.getExpenseElementDuration_1());
            }
            else if (elementID == 2) {
                expense.setQuantity(expensesJson.getExpenseElement_2());
                expense.setDuration(expensesJson.getExpenseElementDuration_2());
            }
            else if (elementID == 3) {
                expense.setQuantity(expensesJson.getExpenseElement_3());
                expense.setDuration(expensesJson.getExpenseElementDuration_3());
            }
            else if (elementID == 4) {
                expense.setQuantity(expensesJson.getExpenseElement_4());
                expense.setDuration(expensesJson.getExpenseElementDuration_4());
            }
            else if (elementID == 5) {
                expense.setQuantity(expensesJson.getExpenseElement_5());
                expense.setDuration(expensesJson.getExpenseElementDuration_5());
            }
            else if (elementID == 6) {
                expense.setQuantity(expensesJson.getExpenseElement_6());
                expense.setDuration(expensesJson.getExpenseElementDuration_6());
            }
            else if (elementID == 7) {
                expense.setQuantity(expensesJson.getExpenseElement_7());
                expense.setDuration(expensesJson.getExpenseElementDuration_7());
            }
            else if (elementID == 8) {
                expense.setQuantity(expensesJson.getExpenseElement_8());
                expense.setDuration(expensesJson.getExpenseElementDuration_8());
            }
            else if (elementID == 9) {
                expense.setQuantity(expensesJson.getExpenseElement_9());
                expense.setDuration(expensesJson.getExpenseElementDuration_9());
            }
            else if (elementID == 10) {
                expense.setQuantity(expensesJson.getExpenseElement_10());
                expense.setDuration(expensesJson.getExpenseElementDuration_10());
            }
            else if (elementID == 11) {
                expense.setQuantity(expensesJson.getExpenseElement_11());
                expense.setDuration(expensesJson.getExpenseElementDuration_11());
            }
            else if (elementID == 12) {
                expense.setQuantity(expensesJson.getExpenseElement_12());
                expense.setDuration(expensesJson.getExpenseElementDuration_12());
            }
            else if (elementID == 13) {
                expense.setQuantity(expensesJson.getExpenseElement_13());
                expense.setDuration(expensesJson.getExpenseElementDuration_13());
            }
            else if (elementID == 14) {
                expense.setQuantity(expensesJson.getExpenseElement_14());
                expense.setDuration(expensesJson.getExpenseElementDuration_14());
            }
            else if (elementID == 15) {
                expense.setQuantity(expensesJson.getExpenseElement_15());
                expense.setDuration(expensesJson.getExpenseElementDuration_15());
            }
            else if (elementID == 16) {
                expense.setQuantity(expensesJson.getExpenseElement_16());
                expense.setDuration(expensesJson.getExpenseElementDuration_16());
            }
            else if (elementID == 17) {
                expense.setQuantity(expensesJson.getExpenseElement_17());
                expense.setDuration(expensesJson.getExpenseElementDuration_17());
            }
            else if (elementID == 18) {
                expense.setQuantity(expensesJson.getExpenseElement_18());
                expense.setDuration(expensesJson.getExpenseElementDuration_18());
            }
            else if (elementID == 19) {
                expense.setQuantity(expensesJson.getExpenseElement_19());
                expense.setDuration(expensesJson.getExpenseElementDuration_19());
            }
            else if (elementID == 20) {
                expense.setQuantity(expensesJson.getExpenseElement_20());
                expense.setDuration(expensesJson.getExpenseElementDuration_20());
            }
            else if (elementID == 21) {
                expense.setQuantity(expensesJson.getExpenseElement_21());
                expense.setDuration(expensesJson.getExpenseElementDuration_21());
            }
            else if (elementID == 22) {
                expense.setQuantity(expensesJson.getExpenseElement_22());
                expense.setDuration(expensesJson.getExpenseElementDuration_22());
            }
            else if (elementID == 23) {
                expense.setQuantity(expensesJson.getExpenseElement_23());
                expense.setDuration(expensesJson.getExpenseElementDuration_23());
            }
            else if (elementID == 24) {
                expense.setQuantity(expensesJson.getExpenseElement_24());
                expense.setDuration(expensesJson.getExpenseElementDuration_24());
            }
            else if (elementID == 25) {
                expense.setQuantity(expensesJson.getExpenseElement_25());
                expense.setDuration(expensesJson.getExpenseElementDuration_25());
            }
            else if (elementID == 26) {
                expense.setQuantity(expensesJson.getExpenseElement_26());
                expense.setDuration(expensesJson.getExpenseElementDuration_26());
            }
            else if (elementID == 27) {
                expense.setQuantity(expensesJson.getExpenseElement_27());
                expense.setDuration(expensesJson.getExpenseElementDuration_27());
            }
            else if (elementID == 28) {
                expense.setQuantity(expensesJson.getExpenseElement_28());
                expense.setDuration(expensesJson.getExpenseElementDuration_28());
            }
            else if (elementID == 29) {
                expense.setQuantity(expensesJson.getExpenseElement_29());
                expense.setDuration(expensesJson.getExpenseElementDuration_29());
            }
            else if (elementID == 30) {
                expense.setQuantity(expensesJson.getExpenseElement_30());
                expense.setDuration(expensesJson.getExpenseElementDuration_30());
            }
            else if (elementID == 31) {
                expense.setQuantity(expensesJson.getExpenseElement_31());
                expense.setDuration(expensesJson.getExpenseElementDuration_31());
            }
            else if (elementID == 32) {
                expense.setQuantity(expensesJson.getExpenseElement_32());
                expense.setDuration(expensesJson.getExpenseElementDuration_32());
            }
            else if (elementID == 33) {
                expense.setQuantity(expensesJson.getExpenseElement_33());
                expense.setDuration(expensesJson.getExpenseElementDuration_33());
            }
            else if (elementID == 34) {
                expense.setQuantity(expensesJson.getExpenseElement_34());
                expense.setDuration(expensesJson.getExpenseElementDuration_34());
            }
            else if (elementID == 35) {
                expense.setQuantity(expensesJson.getExpenseElement_35());
                expense.setDuration(expensesJson.getExpenseElementDuration_35());
            }
            else if (elementID == 36) {
                expense.setQuantity(expensesJson.getExpenseElement_36());
                expense.setDuration(expensesJson.getExpenseElementDuration_36());
            }
            else if (elementID == 37) {
                expense.setQuantity(expensesJson.getExpenseElement_37());
                expense.setDuration(expensesJson.getExpenseElementDuration_37());
            }
            else if (elementID == 38) {
                expense.setQuantity(expensesJson.getExpenseElement_38());
                expense.setDuration(expensesJson.getExpenseElementDuration_38());
            }
            else if (elementID == 39) {
                expense.setQuantity(expensesJson.getExpenseElement_39());
                expense.setDuration(expensesJson.getExpenseElementDuration_39());
            }
            else if (elementID == 40) {
                expense.setQuantity(expensesJson.getExpenseElement_40());
                expense.setDuration(expensesJson.getExpenseElementDuration_40());
            }
            else if (elementID == 41) {
                expense.setQuantity(expensesJson.getExpenseElement_41());
                expense.setDuration(expensesJson.getExpenseElementDuration_41());
            }
            else if (elementID == 42) {
                expense.setQuantity(expensesJson.getExpenseElement_42());
                expense.setDuration(expensesJson.getExpenseElementDuration_42());
            }
            else if (elementID == 43) {
                expense.setQuantity(expensesJson.getExpenseElement_43());
                expense.setDuration(expensesJson.getExpenseElementDuration_43());
            }
            else if (elementID == 44) {
                expense.setQuantity(expensesJson.getExpenseElement_44());
                expense.setDuration(expensesJson.getExpenseElementDuration_44());
            }
            else if (elementID == 45) {
                expense.setQuantity(expensesJson.getExpenseElement_45());
                expense.setDuration(expensesJson.getExpenseElementDuration_45());
            }
            else if (elementID == 46) {
                expense.setQuantity(expensesJson.getExpenseElement_46());
                expense.setDuration(expensesJson.getExpenseElementDuration_46());
            }
            else if (elementID == 47) {
                expense.setQuantity(expensesJson.getExpenseElement_47());
                expense.setDuration(expensesJson.getExpenseElementDuration_47());
            }
            else if (elementID == 48) {
                expense.setQuantity(expensesJson.getExpenseElement_48());
                expense.setDuration(expensesJson.getExpenseElementDuration_48());
            }
            else if (elementID == 49) {
                expense.setQuantity(expensesJson.getExpenseElement_49());
                expense.setDuration(expensesJson.getExpenseElementDuration_49());
            }
            else if (elementID == 50) {
                expense.setQuantity(expensesJson.getExpenseElement_50());
                expense.setDuration(expensesJson.getExpenseElementDuration_50());
            }
            else if (elementID == 51) {
                expense.setQuantity(expensesJson.getExpenseElement_51());
                expense.setDuration(expensesJson.getExpenseElementDuration_51());
            }
            else if (elementID == 52) {
                expense.setQuantity(expensesJson.getExpenseElement_52());
                expense.setDuration(expensesJson.getExpenseElementDuration_52());
            }
            else if (elementID == 53) {
                expense.setQuantity(expensesJson.getExpenseElement_53());
                expense.setDuration(expensesJson.getExpenseElementDuration_53());
            }
            else if (elementID == 54) {
                expense.setQuantity(expensesJson.getExpenseElement_54());
                expense.setDuration(expensesJson.getExpenseElementDuration_54());
            }
            else if (elementID == 55) {
                expense.setQuantity(expensesJson.getExpenseElement_55());
                expense.setDuration(expensesJson.getExpenseElementDuration_55());
            }
            else if (elementID == 56) {
                expense.setQuantity(expensesJson.getExpenseElement_56());
                expense.setDuration(expensesJson.getExpenseElementDuration_56());
            }
            else if (elementID == 57) {
                expense.setQuantity(expensesJson.getExpenseElement_57());
                expense.setDuration(expensesJson.getExpenseElementDuration_57());
            }
            else if (elementID == 58) {
                expense.setQuantity(expensesJson.getExpenseElement_58());
                expense.setDuration(expensesJson.getExpenseElementDuration_58());
            }
            else if (elementID == 59) {
                expense.setQuantity(expensesJson.getExpenseElement_59());
                expense.setDuration(expensesJson.getExpenseElementDuration_59());
            }
            else if (elementID == 60) {
                expense.setQuantity(expensesJson.getExpenseElement_60());
                expense.setDuration(expensesJson.getExpenseElementDuration_60());
            }
            else {
                expense.setQuantity(expensesJson.getExpenseElement_61());
                expense.setDuration(expensesJson.getExpenseElementDuration_61());
            }

            expensesService.save(expense);
        }



        return "quotation/expenses";
    }

}
