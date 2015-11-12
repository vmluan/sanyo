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

            expensesJson.setExpenseElementDuration_1(expenseses.get(0).getDuration());
            expensesJson.setExpenseElementDuration_2(expenseses.get(1).getDuration());
            expensesJson.setExpenseElementDuration_3(expenseses.get(2).getDuration());
            expensesJson.setExpenseElementDuration_4(expenseses.get(3).getDuration());
            expensesJson.setExpenseElementDuration_5(expenseses.get(4).getDuration());
            expensesJson.setExpenseElementDuration_6(expenseses.get(5).getDuration());
            expensesJson.setExpenseElementDuration_7(expenseses.get(6).getDuration());
            expensesJson.setExpenseElementDuration_8(expenseses.get(7).getDuration());
            expensesJson.setExpenseElementDuration_9(expenseses.get(8).getDuration());
            expensesJson.setExpenseElementDuration_10(expenseses.get(9).getDuration());
            expensesJson.setExpenseElementDuration_11(expenseses.get(10).getDuration());
            expensesJson.setExpenseElementDuration_12(expenseses.get(11).getDuration());
            expensesJson.setExpenseElementDuration_13(expenseses.get(12).getDuration());
            expensesJson.setExpenseElementDuration_14(expenseses.get(13).getDuration());
            expensesJson.setExpenseElementDuration_15(expenseses.get(14).getDuration());
            expensesJson.setExpenseElementDuration_16(expenseses.get(15).getDuration());
            expensesJson.setExpenseElementDuration_17(expenseses.get(16).getDuration());
            expensesJson.setExpenseElementDuration_18(expenseses.get(17).getDuration());
            expensesJson.setExpenseElementDuration_19(expenseses.get(18).getDuration());
            expensesJson.setExpenseElementDuration_20(expenseses.get(19).getDuration());
            expensesJson.setExpenseElementDuration_21(expenseses.get(20).getDuration());
            expensesJson.setExpenseElementDuration_22(expenseses.get(21).getDuration());
            expensesJson.setExpenseElementDuration_23(expenseses.get(22).getDuration());
            expensesJson.setExpenseElementDuration_24(expenseses.get(23).getDuration());
            expensesJson.setExpenseElementDuration_25(expenseses.get(24).getDuration());
            expensesJson.setExpenseElementDuration_26(expenseses.get(25).getDuration());
            expensesJson.setExpenseElementDuration_27(expenseses.get(26).getDuration());
            expensesJson.setExpenseElementDuration_28(expenseses.get(27).getDuration());
            expensesJson.setExpenseElementDuration_29(expenseses.get(28).getDuration());
            expensesJson.setExpenseElementDuration_30(expenseses.get(29).getDuration());
            expensesJson.setExpenseElementDuration_31(expenseses.get(30).getDuration());
            expensesJson.setExpenseElementDuration_32(expenseses.get(31).getDuration());
            expensesJson.setExpenseElementDuration_33(expenseses.get(32).getDuration());
            expensesJson.setExpenseElementDuration_34(expenseses.get(33).getDuration());
            expensesJson.setExpenseElementDuration_35(expenseses.get(34).getDuration());
            expensesJson.setExpenseElementDuration_36(expenseses.get(35).getDuration());
            expensesJson.setExpenseElementDuration_37(expenseses.get(36).getDuration());
            expensesJson.setExpenseElementDuration_38(expenseses.get(37).getDuration());
            expensesJson.setExpenseElementDuration_39(expenseses.get(38).getDuration());
            expensesJson.setExpenseElementDuration_40(expenseses.get(39).getDuration());
            expensesJson.setExpenseElementDuration_41(expenseses.get(40).getDuration());
            expensesJson.setExpenseElementDuration_42(expenseses.get(41).getDuration());
            expensesJson.setExpenseElementDuration_43(expenseses.get(42).getDuration());
            expensesJson.setExpenseElementDuration_44(expenseses.get(43).getDuration());
            expensesJson.setExpenseElementDuration_45(expenseses.get(44).getDuration());
            expensesJson.setExpenseElementDuration_46(expenseses.get(45).getDuration());
            expensesJson.setExpenseElementDuration_47(expenseses.get(46).getDuration());
            expensesJson.setExpenseElementDuration_48(expenseses.get(47).getDuration());
            expensesJson.setExpenseElementDuration_49(expenseses.get(48).getDuration());
            expensesJson.setExpenseElementDuration_50(expenseses.get(49).getDuration());
            expensesJson.setExpenseElementDuration_51(expenseses.get(50).getDuration());
            expensesJson.setExpenseElementDuration_52(expenseses.get(51).getDuration());
            expensesJson.setExpenseElementDuration_53(expenseses.get(52).getDuration());
            expensesJson.setExpenseElementDuration_54(expenseses.get(53).getDuration());
            expensesJson.setExpenseElementDuration_55(expenseses.get(54).getDuration());
            expensesJson.setExpenseElementDuration_56(expenseses.get(55).getDuration());
            expensesJson.setExpenseElementDuration_57(expenseses.get(56).getDuration());
            expensesJson.setExpenseElementDuration_58(expenseses.get(57).getDuration());
            expensesJson.setExpenseElementDuration_59(expenseses.get(58).getDuration());
            expensesJson.setExpenseElementDuration_60(expenseses.get(59).getDuration());
            expensesJson.setExpenseElementDuration_61(expenseses.get(60).getDuration());

            expensesJson.setExpenseElementRate_1(expenseses.get(0).getRate());
            expensesJson.setExpenseElementRate_2(expenseses.get(1).getRate());
            expensesJson.setExpenseElementRate_3(expenseses.get(2).getRate());
            expensesJson.setExpenseElementRate_4(expenseses.get(3).getRate());
            expensesJson.setExpenseElementRate_5(expenseses.get(4).getRate());
            expensesJson.setExpenseElementRate_6(expenseses.get(5).getRate());
            expensesJson.setExpenseElementRate_7(expenseses.get(6).getRate());
            expensesJson.setExpenseElementRate_8(expenseses.get(7).getRate());
            expensesJson.setExpenseElementRate_9(expenseses.get(8).getRate());
            expensesJson.setExpenseElementRate_10(expenseses.get(9).getRate());
            expensesJson.setExpenseElementRate_11(expenseses.get(10).getRate());
            expensesJson.setExpenseElementRate_12(expenseses.get(11).getRate());
            expensesJson.setExpenseElementRate_13(expenseses.get(12).getRate());
            expensesJson.setExpenseElementRate_14(expenseses.get(13).getRate());
            expensesJson.setExpenseElementRate_15(expenseses.get(14).getRate());
            expensesJson.setExpenseElementRate_16(expenseses.get(15).getRate());
            expensesJson.setExpenseElementRate_17(expenseses.get(16).getRate());
            expensesJson.setExpenseElementRate_18(expenseses.get(17).getRate());
            expensesJson.setExpenseElementRate_19(expenseses.get(18).getRate());
            expensesJson.setExpenseElementRate_20(expenseses.get(19).getRate());
            expensesJson.setExpenseElementRate_21(expenseses.get(20).getRate());
            expensesJson.setExpenseElementRate_22(expenseses.get(21).getRate());
            expensesJson.setExpenseElementRate_23(expenseses.get(22).getRate());
            expensesJson.setExpenseElementRate_24(expenseses.get(23).getRate());
            expensesJson.setExpenseElementRate_25(expenseses.get(24).getRate());
            expensesJson.setExpenseElementRate_26(expenseses.get(25).getRate());
            expensesJson.setExpenseElementRate_27(expenseses.get(26).getRate());
            expensesJson.setExpenseElementRate_28(expenseses.get(27).getRate());
            expensesJson.setExpenseElementRate_29(expenseses.get(28).getRate());
            expensesJson.setExpenseElementRate_30(expenseses.get(29).getRate());
            expensesJson.setExpenseElementRate_31(expenseses.get(30).getRate());
            expensesJson.setExpenseElementRate_32(expenseses.get(31).getRate());
            expensesJson.setExpenseElementRate_33(expenseses.get(32).getRate());
            expensesJson.setExpenseElementRate_34(expenseses.get(33).getRate());
            expensesJson.setExpenseElementRate_35(expenseses.get(34).getRate());
            expensesJson.setExpenseElementRate_36(expenseses.get(35).getRate());
            expensesJson.setExpenseElementRate_37(expenseses.get(36).getRate());
            expensesJson.setExpenseElementRate_38(expenseses.get(37).getRate());
            expensesJson.setExpenseElementRate_39(expenseses.get(38).getRate());
            expensesJson.setExpenseElementRate_40(expenseses.get(39).getRate());
            expensesJson.setExpenseElementRate_41(expenseses.get(40).getRate());
            expensesJson.setExpenseElementRate_42(expenseses.get(41).getRate());
            expensesJson.setExpenseElementRate_43(expenseses.get(42).getRate());
            expensesJson.setExpenseElementRate_44(expenseses.get(43).getRate());
            expensesJson.setExpenseElementRate_45(expenseses.get(44).getRate());
            expensesJson.setExpenseElementRate_46(expenseses.get(45).getRate());
            expensesJson.setExpenseElementRate_47(expenseses.get(46).getRate());
            expensesJson.setExpenseElementRate_48(expenseses.get(47).getRate());
            expensesJson.setExpenseElementRate_49(expenseses.get(48).getRate());
            expensesJson.setExpenseElementRate_50(expenseses.get(49).getRate());
            expensesJson.setExpenseElementRate_51(expenseses.get(50).getRate());
            expensesJson.setExpenseElementRate_52(expenseses.get(51).getRate());
            expensesJson.setExpenseElementRate_53(expenseses.get(52).getRate());
            expensesJson.setExpenseElementRate_54(expenseses.get(53).getRate());
            expensesJson.setExpenseElementRate_55(expenseses.get(54).getRate());
            expensesJson.setExpenseElementRate_56(expenseses.get(55).getRate());
            expensesJson.setExpenseElementRate_57(expenseses.get(56).getRate());
            expensesJson.setExpenseElementRate_58(expenseses.get(57).getRate());
            expensesJson.setExpenseElementRate_59(expenseses.get(58).getRate());
            expensesJson.setExpenseElementRate_60(expenseses.get(59).getRate());
            expensesJson.setExpenseElementRate_61(expenseses.get(60).getRate());

            expensesJson.setExpenseElementSum_1(expenseses.get(0).getSum());
            expensesJson.setExpenseElementSum_2(expenseses.get(1).getSum());
            expensesJson.setExpenseElementSum_3(expenseses.get(2).getSum());
            expensesJson.setExpenseElementSum_4(expenseses.get(3).getSum());
            expensesJson.setExpenseElementSum_5(expenseses.get(4).getSum());
            expensesJson.setExpenseElementSum_6(expenseses.get(5).getSum());
            expensesJson.setExpenseElementSum_7(expenseses.get(6).getSum());
            expensesJson.setExpenseElementSum_8(expenseses.get(7).getSum());
            expensesJson.setExpenseElementSum_9(expenseses.get(8).getSum());
            expensesJson.setExpenseElementSum_10(expenseses.get(9).getSum());
            expensesJson.setExpenseElementSum_11(expenseses.get(10).getSum());
            expensesJson.setExpenseElementSum_12(expenseses.get(11).getSum());
            expensesJson.setExpenseElementSum_13(expenseses.get(12).getSum());
            expensesJson.setExpenseElementSum_14(expenseses.get(13).getSum());
            expensesJson.setExpenseElementSum_15(expenseses.get(14).getSum());
            expensesJson.setExpenseElementSum_16(expenseses.get(15).getSum());
            expensesJson.setExpenseElementSum_17(expenseses.get(16).getSum());
            expensesJson.setExpenseElementSum_18(expenseses.get(17).getSum());
            expensesJson.setExpenseElementSum_19(expenseses.get(18).getSum());
            expensesJson.setExpenseElementSum_20(expenseses.get(19).getSum());
            expensesJson.setExpenseElementSum_21(expenseses.get(20).getSum());
            expensesJson.setExpenseElementSum_22(expenseses.get(21).getSum());
            expensesJson.setExpenseElementSum_23(expenseses.get(22).getSum());
            expensesJson.setExpenseElementSum_24(expenseses.get(23).getSum());
            expensesJson.setExpenseElementSum_25(expenseses.get(24).getSum());
            expensesJson.setExpenseElementSum_26(expenseses.get(25).getSum());
            expensesJson.setExpenseElementSum_27(expenseses.get(26).getSum());
            expensesJson.setExpenseElementSum_28(expenseses.get(27).getSum());
            expensesJson.setExpenseElementSum_29(expenseses.get(28).getSum());
            expensesJson.setExpenseElementSum_30(expenseses.get(29).getSum());
            expensesJson.setExpenseElementSum_31(expenseses.get(30).getSum());
            expensesJson.setExpenseElementSum_32(expenseses.get(31).getSum());
            expensesJson.setExpenseElementSum_33(expenseses.get(32).getSum());
            expensesJson.setExpenseElementSum_34(expenseses.get(33).getSum());
            expensesJson.setExpenseElementSum_35(expenseses.get(34).getSum());
            expensesJson.setExpenseElementSum_36(expenseses.get(35).getSum());
            expensesJson.setExpenseElementSum_37(expenseses.get(36).getSum());
            expensesJson.setExpenseElementSum_38(expenseses.get(37).getSum());
            expensesJson.setExpenseElementSum_39(expenseses.get(38).getSum());
            expensesJson.setExpenseElementSum_40(expenseses.get(39).getSum());
            expensesJson.setExpenseElementSum_41(expenseses.get(40).getSum());
            expensesJson.setExpenseElementSum_42(expenseses.get(41).getSum());
            expensesJson.setExpenseElementSum_43(expenseses.get(42).getSum());
            expensesJson.setExpenseElementSum_44(expenseses.get(43).getSum());
            expensesJson.setExpenseElementSum_45(expenseses.get(44).getSum());
            expensesJson.setExpenseElementSum_46(expenseses.get(45).getSum());
            expensesJson.setExpenseElementSum_47(expenseses.get(46).getSum());
            expensesJson.setExpenseElementSum_48(expenseses.get(47).getSum());
            expensesJson.setExpenseElementSum_49(expenseses.get(48).getSum());
            expensesJson.setExpenseElementSum_50(expenseses.get(49).getSum());
            expensesJson.setExpenseElementSum_51(expenseses.get(50).getSum());
            expensesJson.setExpenseElementSum_52(expenseses.get(51).getSum());
            expensesJson.setExpenseElementSum_53(expenseses.get(52).getSum());
            expensesJson.setExpenseElementSum_54(expenseses.get(53).getSum());
            expensesJson.setExpenseElementSum_55(expenseses.get(54).getSum());
            expensesJson.setExpenseElementSum_56(expenseses.get(55).getSum());
            expensesJson.setExpenseElementSum_57(expenseses.get(56).getSum());
            expensesJson.setExpenseElementSum_58(expenseses.get(57).getSum());
            expensesJson.setExpenseElementSum_59(expenseses.get(58).getSum());
            expensesJson.setExpenseElementSum_60(expenseses.get(59).getSum());
            expensesJson.setExpenseElementSum_61(expenseses.get(60).getSum());

            expensesJson.setExpenseElementRemark_1(expenseses.get(0).getRemark());
            expensesJson.setExpenseElementRemark_2(expenseses.get(1).getRemark());
            expensesJson.setExpenseElementRemark_3(expenseses.get(2).getRemark());
            expensesJson.setExpenseElementRemark_4(expenseses.get(3).getRemark());
            expensesJson.setExpenseElementRemark_5(expenseses.get(4).getRemark());
            expensesJson.setExpenseElementRemark_6(expenseses.get(5).getRemark());
            expensesJson.setExpenseElementRemark_7(expenseses.get(6).getRemark());
            expensesJson.setExpenseElementRemark_8(expenseses.get(7).getRemark());
            expensesJson.setExpenseElementRemark_9(expenseses.get(8).getRemark());
            expensesJson.setExpenseElementRemark_10(expenseses.get(9).getRemark());
            expensesJson.setExpenseElementRemark_11(expenseses.get(10).getRemark());
            expensesJson.setExpenseElementRemark_12(expenseses.get(11).getRemark());
            expensesJson.setExpenseElementRemark_13(expenseses.get(12).getRemark());
            expensesJson.setExpenseElementRemark_14(expenseses.get(13).getRemark());
            expensesJson.setExpenseElementRemark_15(expenseses.get(14).getRemark());
            expensesJson.setExpenseElementRemark_16(expenseses.get(15).getRemark());
            expensesJson.setExpenseElementRemark_17(expenseses.get(16).getRemark());
            expensesJson.setExpenseElementRemark_18(expenseses.get(17).getRemark());
            expensesJson.setExpenseElementRemark_19(expenseses.get(18).getRemark());
            expensesJson.setExpenseElementRemark_20(expenseses.get(19).getRemark());
            expensesJson.setExpenseElementRemark_21(expenseses.get(20).getRemark());
            expensesJson.setExpenseElementRemark_22(expenseses.get(21).getRemark());
            expensesJson.setExpenseElementRemark_23(expenseses.get(22).getRemark());
            expensesJson.setExpenseElementRemark_24(expenseses.get(23).getRemark());
            expensesJson.setExpenseElementRemark_25(expenseses.get(24).getRemark());
            expensesJson.setExpenseElementRemark_26(expenseses.get(25).getRemark());
            expensesJson.setExpenseElementRemark_27(expenseses.get(26).getRemark());
            expensesJson.setExpenseElementRemark_28(expenseses.get(27).getRemark());
            expensesJson.setExpenseElementRemark_29(expenseses.get(28).getRemark());
            expensesJson.setExpenseElementRemark_30(expenseses.get(29).getRemark());
            expensesJson.setExpenseElementRemark_31(expenseses.get(30).getRemark());
            expensesJson.setExpenseElementRemark_32(expenseses.get(31).getRemark());
            expensesJson.setExpenseElementRemark_33(expenseses.get(32).getRemark());
            expensesJson.setExpenseElementRemark_34(expenseses.get(33).getRemark());
            expensesJson.setExpenseElementRemark_35(expenseses.get(34).getRemark());
            expensesJson.setExpenseElementRemark_36(expenseses.get(35).getRemark());
            expensesJson.setExpenseElementRemark_37(expenseses.get(36).getRemark());
            expensesJson.setExpenseElementRemark_38(expenseses.get(37).getRemark());
            expensesJson.setExpenseElementRemark_39(expenseses.get(38).getRemark());
            expensesJson.setExpenseElementRemark_40(expenseses.get(39).getRemark());
            expensesJson.setExpenseElementRemark_41(expenseses.get(40).getRemark());
            expensesJson.setExpenseElementRemark_42(expenseses.get(41).getRemark());
            expensesJson.setExpenseElementRemark_43(expenseses.get(42).getRemark());
            expensesJson.setExpenseElementRemark_44(expenseses.get(43).getRemark());
            expensesJson.setExpenseElementRemark_45(expenseses.get(44).getRemark());
            expensesJson.setExpenseElementRemark_46(expenseses.get(45).getRemark());
            expensesJson.setExpenseElementRemark_47(expenseses.get(46).getRemark());
            expensesJson.setExpenseElementRemark_48(expenseses.get(47).getRemark());
            expensesJson.setExpenseElementRemark_49(expenseses.get(48).getRemark());
            expensesJson.setExpenseElementRemark_50(expenseses.get(49).getRemark());
            expensesJson.setExpenseElementRemark_51(expenseses.get(50).getRemark());
            expensesJson.setExpenseElementRemark_52(expenseses.get(51).getRemark());
            expensesJson.setExpenseElementRemark_53(expenseses.get(52).getRemark());
            expensesJson.setExpenseElementRemark_54(expenseses.get(53).getRemark());
            expensesJson.setExpenseElementRemark_55(expenseses.get(54).getRemark());
            expensesJson.setExpenseElementRemark_56(expenseses.get(55).getRemark());
            expensesJson.setExpenseElementRemark_57(expenseses.get(56).getRemark());
            expensesJson.setExpenseElementRemark_58(expenseses.get(57).getRemark());
            expensesJson.setExpenseElementRemark_59(expenseses.get(58).getRemark());
            expensesJson.setExpenseElementRemark_60(expenseses.get(59).getRemark());
            expensesJson.setExpenseElementRemark_61(expenseses.get(60).getRemark());
        }
        else {
            //Bring the default RATE to the form if this is the new Project (Doesn't have any expenses records yet)
            List<ExpenseElements> expenseElements = expenseElementsService.findAll();
            for(ExpenseElements expenseElement : expenseElements){
                if (expenseElement.getExpenseElementID() ==1) expensesJson.setExpenseElementRate_1(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==2) expensesJson.setExpenseElementRate_2(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==3) expensesJson.setExpenseElementRate_3(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==4) expensesJson.setExpenseElementRate_4(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==5) expensesJson.setExpenseElementRate_5(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==6) expensesJson.setExpenseElementRate_6(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==7) expensesJson.setExpenseElementRate_7(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==8) expensesJson.setExpenseElementRate_8(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==9) expensesJson.setExpenseElementRate_9(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==10) expensesJson.setExpenseElementRate_10(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==11) expensesJson.setExpenseElementRate_11(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==12) expensesJson.setExpenseElementRate_12(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==13) expensesJson.setExpenseElementRate_13(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==14) expensesJson.setExpenseElementRate_14(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==15) expensesJson.setExpenseElementRate_15(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==16) expensesJson.setExpenseElementRate_16(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==17) expensesJson.setExpenseElementRate_17(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==18) expensesJson.setExpenseElementRate_18(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==19) expensesJson.setExpenseElementRate_19(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==20) expensesJson.setExpenseElementRate_20(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==21) expensesJson.setExpenseElementRate_21(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==22) expensesJson.setExpenseElementRate_22(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==23) expensesJson.setExpenseElementRate_23(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==24) expensesJson.setExpenseElementRate_24(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==25) expensesJson.setExpenseElementRate_25(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==26) expensesJson.setExpenseElementRate_26(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==27) expensesJson.setExpenseElementRate_27(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==28) expensesJson.setExpenseElementRate_28(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==29) expensesJson.setExpenseElementRate_29(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==30) expensesJson.setExpenseElementRate_30(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==31) expensesJson.setExpenseElementRate_31(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==32) expensesJson.setExpenseElementRate_32(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==33) expensesJson.setExpenseElementRate_33(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==34) expensesJson.setExpenseElementRate_34(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==35) expensesJson.setExpenseElementRate_35(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==36) expensesJson.setExpenseElementRate_36(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==37) expensesJson.setExpenseElementRate_37(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==38) expensesJson.setExpenseElementRate_38(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==39) expensesJson.setExpenseElementRate_39(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==40) expensesJson.setExpenseElementRate_40(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==41) expensesJson.setExpenseElementRate_41(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==42) expensesJson.setExpenseElementRate_42(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==43) expensesJson.setExpenseElementRate_43(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==44) expensesJson.setExpenseElementRate_44(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==45) expensesJson.setExpenseElementRate_45(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==46) expensesJson.setExpenseElementRate_46(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==47) expensesJson.setExpenseElementRate_47(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==48) expensesJson.setExpenseElementRate_48(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==49) expensesJson.setExpenseElementRate_49(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==50) expensesJson.setExpenseElementRate_50(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==51) expensesJson.setExpenseElementRate_51(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==52) expensesJson.setExpenseElementRate_52(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==53) expensesJson.setExpenseElementRate_53(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==54) expensesJson.setExpenseElementRate_54(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==55) expensesJson.setExpenseElementRate_55(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==56) expensesJson.setExpenseElementRate_56(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==57) expensesJson.setExpenseElementRate_57(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==58) expensesJson.setExpenseElementRate_58(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==59) expensesJson.setExpenseElementRate_59(expenseElement.getDefaultRate());
                else if (expenseElement.getExpenseElementID() ==60) expensesJson.setExpenseElementRate_60(expenseElement.getDefaultRate());
                else expensesJson.setExpenseElementRate_61(expenseElement.getDefaultRate());

            }
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
                expense.setRate(expensesJson.getExpenseElementRate_1());
                expense.setSum(expensesJson.getExpenseElementSum_1());
                expense.setRemark(expensesJson.getExpenseElementRemark_1());
            }
            else if (elementID == 2) {
                expense.setQuantity(expensesJson.getExpenseElement_2());
                expense.setDuration(expensesJson.getExpenseElementDuration_2());
                expense.setRate(expensesJson.getExpenseElementRate_2());
                expense.setSum(expensesJson.getExpenseElementSum_2());
                expense.setRemark(expensesJson.getExpenseElementRemark_2());
            }
            else if (elementID == 3) {
                expense.setQuantity(expensesJson.getExpenseElement_3());
                expense.setDuration(expensesJson.getExpenseElementDuration_3());
                expense.setRate(expensesJson.getExpenseElementRate_3());
                expense.setSum(expensesJson.getExpenseElementSum_3());
                expense.setRemark(expensesJson.getExpenseElementRemark_3());
            }
            else if (elementID == 4) {
                expense.setQuantity(expensesJson.getExpenseElement_4());
                expense.setDuration(expensesJson.getExpenseElementDuration_4());
                expense.setRate(expensesJson.getExpenseElementRate_4());
                expense.setSum(expensesJson.getExpenseElementSum_4());
                expense.setRemark(expensesJson.getExpenseElementRemark_4());
            }
            else if (elementID == 5) {
                expense.setQuantity(expensesJson.getExpenseElement_5());
                expense.setDuration(expensesJson.getExpenseElementDuration_5());
                expense.setRate(expensesJson.getExpenseElementRate_5());
                expense.setSum(expensesJson.getExpenseElementSum_5());
                expense.setRemark(expensesJson.getExpenseElementRemark_5());
            }
            else if (elementID == 6) {
                expense.setQuantity(expensesJson.getExpenseElement_6());
                expense.setDuration(expensesJson.getExpenseElementDuration_6());
                expense.setRate(expensesJson.getExpenseElementRate_6());
                expense.setSum(expensesJson.getExpenseElementSum_6());
                expense.setRemark(expensesJson.getExpenseElementRemark_6());
            }
            else if (elementID == 7) {
                expense.setQuantity(expensesJson.getExpenseElement_7());
                expense.setDuration(expensesJson.getExpenseElementDuration_7());
                expense.setRate(expensesJson.getExpenseElementRate_7());
                expense.setSum(expensesJson.getExpenseElementSum_7());
                expense.setRemark(expensesJson.getExpenseElementRemark_7());
            }
            else if (elementID == 8) {
                expense.setQuantity(expensesJson.getExpenseElement_8());
                expense.setDuration(expensesJson.getExpenseElementDuration_8());
                expense.setRate(expensesJson.getExpenseElementRate_8());
                expense.setSum(expensesJson.getExpenseElementSum_8());
                expense.setRemark(expensesJson.getExpenseElementRemark_8());
            }
            else if (elementID == 9) {
                expense.setQuantity(expensesJson.getExpenseElement_9());
                expense.setDuration(expensesJson.getExpenseElementDuration_9());
                expense.setRate(expensesJson.getExpenseElementRate_9());
                expense.setSum(expensesJson.getExpenseElementSum_9());
                expense.setRemark(expensesJson.getExpenseElementRemark_9());
            }
            else if (elementID == 10) {
                expense.setQuantity(expensesJson.getExpenseElement_10());
                expense.setDuration(expensesJson.getExpenseElementDuration_10());
                expense.setRate(expensesJson.getExpenseElementRate_10());
                expense.setSum(expensesJson.getExpenseElementSum_10());
                expense.setRemark(expensesJson.getExpenseElementRemark_10());
            }
            else if (elementID == 11) {
                expense.setQuantity(expensesJson.getExpenseElement_11());
                expense.setDuration(expensesJson.getExpenseElementDuration_11());
                expense.setRate(expensesJson.getExpenseElementRate_11());
                expense.setSum(expensesJson.getExpenseElementSum_11());
                expense.setRemark(expensesJson.getExpenseElementRemark_11());
            }
            else if (elementID == 12) {
                expense.setQuantity(expensesJson.getExpenseElement_12());
                expense.setDuration(expensesJson.getExpenseElementDuration_12());
                expense.setRate(expensesJson.getExpenseElementRate_12());
                expense.setSum(expensesJson.getExpenseElementSum_12());
                expense.setRemark(expensesJson.getExpenseElementRemark_12());
            }
            else if (elementID == 13) {
                expense.setQuantity(expensesJson.getExpenseElement_13());
                expense.setDuration(expensesJson.getExpenseElementDuration_13());
                expense.setRate(expensesJson.getExpenseElementRate_13());
                expense.setSum(expensesJson.getExpenseElementSum_13());
                expense.setRemark(expensesJson.getExpenseElementRemark_13());
            }
            else if (elementID == 14) {
                expense.setQuantity(expensesJson.getExpenseElement_14());
                expense.setDuration(expensesJson.getExpenseElementDuration_14());
                expense.setRate(expensesJson.getExpenseElementRate_14());
                expense.setSum(expensesJson.getExpenseElementSum_14());
                expense.setRemark(expensesJson.getExpenseElementRemark_14());
            }
            else if (elementID == 15) {
                expense.setQuantity(expensesJson.getExpenseElement_15());
                expense.setDuration(expensesJson.getExpenseElementDuration_15());
                expense.setRate(expensesJson.getExpenseElementRate_15());
                expense.setSum(expensesJson.getExpenseElementSum_15());
                expense.setRemark(expensesJson.getExpenseElementRemark_15());
            }
            else if (elementID == 16) {
                expense.setQuantity(expensesJson.getExpenseElement_16());
                expense.setDuration(expensesJson.getExpenseElementDuration_16());
                expense.setRate(expensesJson.getExpenseElementRate_16());
                expense.setSum(expensesJson.getExpenseElementSum_16());
                expense.setRemark(expensesJson.getExpenseElementRemark_16());
            }
            else if (elementID == 17) {
                expense.setQuantity(expensesJson.getExpenseElement_17());
                expense.setDuration(expensesJson.getExpenseElementDuration_17());
                expense.setRate(expensesJson.getExpenseElementRate_17());
                expense.setSum(expensesJson.getExpenseElementSum_17());
                expense.setRemark(expensesJson.getExpenseElementRemark_17());
            }
            else if (elementID == 18) {
                expense.setQuantity(expensesJson.getExpenseElement_18());
                expense.setDuration(expensesJson.getExpenseElementDuration_18());
                expense.setRate(expensesJson.getExpenseElementRate_18());
                expense.setSum(expensesJson.getExpenseElementSum_18());
                expense.setRemark(expensesJson.getExpenseElementRemark_18());
            }
            else if (elementID == 19) {
                expense.setQuantity(expensesJson.getExpenseElement_19());
                expense.setDuration(expensesJson.getExpenseElementDuration_19());
                expense.setRate(expensesJson.getExpenseElementRate_19());
                expense.setSum(expensesJson.getExpenseElementSum_19());
                expense.setRemark(expensesJson.getExpenseElementRemark_19());
            }
            else if (elementID == 20) {
                expense.setQuantity(expensesJson.getExpenseElement_20());
                expense.setDuration(expensesJson.getExpenseElementDuration_20());
                expense.setRate(expensesJson.getExpenseElementRate_20());
                expense.setSum(expensesJson.getExpenseElementSum_20());
                expense.setRemark(expensesJson.getExpenseElementRemark_20());
            }
            else if (elementID == 21) {
                expense.setQuantity(expensesJson.getExpenseElement_21());
                expense.setDuration(expensesJson.getExpenseElementDuration_21());
                expense.setRate(expensesJson.getExpenseElementRate_21());
                expense.setSum(expensesJson.getExpenseElementSum_21());
                expense.setRemark(expensesJson.getExpenseElementRemark_21());
            }
            else if (elementID == 22) {
                expense.setQuantity(expensesJson.getExpenseElement_22());
                expense.setDuration(expensesJson.getExpenseElementDuration_22());
                expense.setRate(expensesJson.getExpenseElementRate_22());
                expense.setSum(expensesJson.getExpenseElementSum_22());
                expense.setRemark(expensesJson.getExpenseElementRemark_22());
            }
            else if (elementID == 23) {
                expense.setQuantity(expensesJson.getExpenseElement_23());
                expense.setDuration(expensesJson.getExpenseElementDuration_23());
                expense.setRate(expensesJson.getExpenseElementRate_23());
                expense.setSum(expensesJson.getExpenseElementSum_23());
                expense.setRemark(expensesJson.getExpenseElementRemark_23());
            }
            else if (elementID == 24) {
                expense.setQuantity(expensesJson.getExpenseElement_24());
                expense.setDuration(expensesJson.getExpenseElementDuration_24());
                expense.setRate(expensesJson.getExpenseElementRate_24());
                expense.setSum(expensesJson.getExpenseElementSum_24());
                expense.setRemark(expensesJson.getExpenseElementRemark_24());
            }
            else if (elementID == 25) {
                expense.setQuantity(expensesJson.getExpenseElement_25());
                expense.setDuration(expensesJson.getExpenseElementDuration_25());
                expense.setRate(expensesJson.getExpenseElementRate_25());
                expense.setSum(expensesJson.getExpenseElementSum_25());
                expense.setRemark(expensesJson.getExpenseElementRemark_25());
            }
            else if (elementID == 26) {
                expense.setQuantity(expensesJson.getExpenseElement_26());
                expense.setDuration(expensesJson.getExpenseElementDuration_26());
                expense.setRate(expensesJson.getExpenseElementRate_26());
                expense.setSum(expensesJson.getExpenseElementSum_26());
                expense.setRemark(expensesJson.getExpenseElementRemark_26());
            }
            else if (elementID == 27) {
                expense.setQuantity(expensesJson.getExpenseElement_27());
                expense.setDuration(expensesJson.getExpenseElementDuration_27());
                expense.setRate(expensesJson.getExpenseElementRate_27());
                expense.setSum(expensesJson.getExpenseElementSum_27());
                expense.setRemark(expensesJson.getExpenseElementRemark_27());
            }
            else if (elementID == 28) {
                expense.setQuantity(expensesJson.getExpenseElement_28());
                expense.setDuration(expensesJson.getExpenseElementDuration_28());
                expense.setRate(expensesJson.getExpenseElementRate_28());
                expense.setSum(expensesJson.getExpenseElementSum_28());
                expense.setRemark(expensesJson.getExpenseElementRemark_28());
            }
            else if (elementID == 29) {
                expense.setQuantity(expensesJson.getExpenseElement_29());
                expense.setDuration(expensesJson.getExpenseElementDuration_29());
                expense.setRate(expensesJson.getExpenseElementRate_29());
                expense.setSum(expensesJson.getExpenseElementSum_29());
                expense.setRemark(expensesJson.getExpenseElementRemark_29());
            }
            else if (elementID == 30) {
                expense.setQuantity(expensesJson.getExpenseElement_30());
                expense.setDuration(expensesJson.getExpenseElementDuration_30());
                expense.setRate(expensesJson.getExpenseElementRate_30());
                expense.setSum(expensesJson.getExpenseElementSum_30());
                expense.setRemark(expensesJson.getExpenseElementRemark_30());
            }
            else if (elementID == 31) {
                expense.setQuantity(expensesJson.getExpenseElement_31());
                expense.setDuration(expensesJson.getExpenseElementDuration_31());
                expense.setRate(expensesJson.getExpenseElementRate_31());
                expense.setSum(expensesJson.getExpenseElementSum_31());
                expense.setRemark(expensesJson.getExpenseElementRemark_31());
            }
            else if (elementID == 32) {
                expense.setQuantity(expensesJson.getExpenseElement_32());
                expense.setDuration(expensesJson.getExpenseElementDuration_32());
                expense.setRate(expensesJson.getExpenseElementRate_32());
                expense.setSum(expensesJson.getExpenseElementSum_32());
                expense.setRemark(expensesJson.getExpenseElementRemark_32());
            }
            else if (elementID == 33) {
                expense.setQuantity(expensesJson.getExpenseElement_33());
                expense.setDuration(expensesJson.getExpenseElementDuration_33());
                expense.setRate(expensesJson.getExpenseElementRate_33());
                expense.setSum(expensesJson.getExpenseElementSum_33());
                expense.setRemark(expensesJson.getExpenseElementRemark_33());
            }
            else if (elementID == 34) {
                expense.setQuantity(expensesJson.getExpenseElement_34());
                expense.setDuration(expensesJson.getExpenseElementDuration_34());
                expense.setRate(expensesJson.getExpenseElementRate_34());
                expense.setSum(expensesJson.getExpenseElementSum_34());
                expense.setRemark(expensesJson.getExpenseElementRemark_34());
            }
            else if (elementID == 35) {
                expense.setQuantity(expensesJson.getExpenseElement_35());
                expense.setDuration(expensesJson.getExpenseElementDuration_35());
                expense.setRate(expensesJson.getExpenseElementRate_35());
                expense.setSum(expensesJson.getExpenseElementSum_35());
                expense.setRemark(expensesJson.getExpenseElementRemark_35());
            }
            else if (elementID == 36) {
                expense.setQuantity(expensesJson.getExpenseElement_36());
                expense.setDuration(expensesJson.getExpenseElementDuration_36());
                expense.setRate(expensesJson.getExpenseElementRate_36());
                expense.setSum(expensesJson.getExpenseElementSum_36());
                expense.setRemark(expensesJson.getExpenseElementRemark_36());
            }
            else if (elementID == 37) {
                expense.setQuantity(expensesJson.getExpenseElement_37());
                expense.setDuration(expensesJson.getExpenseElementDuration_37());
                expense.setRate(expensesJson.getExpenseElementRate_37());
                expense.setSum(expensesJson.getExpenseElementSum_37());
                expense.setRemark(expensesJson.getExpenseElementRemark_37());
            }
            else if (elementID == 38) {
                expense.setQuantity(expensesJson.getExpenseElement_38());
                expense.setDuration(expensesJson.getExpenseElementDuration_38());
                expense.setRate(expensesJson.getExpenseElementRate_38());
                expense.setSum(expensesJson.getExpenseElementSum_38());
                expense.setRemark(expensesJson.getExpenseElementRemark_38());
            }
            else if (elementID == 39) {
                expense.setQuantity(expensesJson.getExpenseElement_39());
                expense.setDuration(expensesJson.getExpenseElementDuration_39());
                expense.setRate(expensesJson.getExpenseElementRate_39());
                expense.setSum(expensesJson.getExpenseElementSum_39());
                expense.setRemark(expensesJson.getExpenseElementRemark_39());
            }
            else if (elementID == 40) {
                expense.setQuantity(expensesJson.getExpenseElement_40());
                expense.setDuration(expensesJson.getExpenseElementDuration_40());
                expense.setRate(expensesJson.getExpenseElementRate_40());
                expense.setSum(expensesJson.getExpenseElementSum_40());
                expense.setRemark(expensesJson.getExpenseElementRemark_40());
            }
            else if (elementID == 41) {
                expense.setQuantity(expensesJson.getExpenseElement_41());
                expense.setDuration(expensesJson.getExpenseElementDuration_41());
                expense.setRate(expensesJson.getExpenseElementRate_41());
                expense.setSum(expensesJson.getExpenseElementSum_41());
                expense.setRemark(expensesJson.getExpenseElementRemark_41());
            }
            else if (elementID == 42) {
                expense.setQuantity(expensesJson.getExpenseElement_42());
                expense.setDuration(expensesJson.getExpenseElementDuration_42());
                expense.setRate(expensesJson.getExpenseElementRate_42());
                expense.setSum(expensesJson.getExpenseElementSum_42());
                expense.setRemark(expensesJson.getExpenseElementRemark_42());
            }
            else if (elementID == 43) {
                expense.setQuantity(expensesJson.getExpenseElement_43());
                expense.setDuration(expensesJson.getExpenseElementDuration_43());
                expense.setRate(expensesJson.getExpenseElementRate_43());
                expense.setSum(expensesJson.getExpenseElementSum_43());
                expense.setRemark(expensesJson.getExpenseElementRemark_43());
            }
            else if (elementID == 44) {
                expense.setQuantity(expensesJson.getExpenseElement_44());
                expense.setDuration(expensesJson.getExpenseElementDuration_44());
                expense.setRate(expensesJson.getExpenseElementRate_44());
                expense.setSum(expensesJson.getExpenseElementSum_44());
                expense.setRemark(expensesJson.getExpenseElementRemark_44());
            }
            else if (elementID == 45) {
                expense.setQuantity(expensesJson.getExpenseElement_45());
                expense.setDuration(expensesJson.getExpenseElementDuration_45());
                expense.setRate(expensesJson.getExpenseElementRate_45());
                expense.setSum(expensesJson.getExpenseElementSum_45());
                expense.setRemark(expensesJson.getExpenseElementRemark_45());
            }
            else if (elementID == 46) {
                expense.setQuantity(expensesJson.getExpenseElement_46());
                expense.setDuration(expensesJson.getExpenseElementDuration_46());
                expense.setRate(expensesJson.getExpenseElementRate_46());
                expense.setSum(expensesJson.getExpenseElementSum_46());
                expense.setRemark(expensesJson.getExpenseElementRemark_46());
            }
            else if (elementID == 47) {
                expense.setQuantity(expensesJson.getExpenseElement_47());
                expense.setDuration(expensesJson.getExpenseElementDuration_47());
                expense.setRate(expensesJson.getExpenseElementRate_47());
                expense.setSum(expensesJson.getExpenseElementSum_47());
                expense.setRemark(expensesJson.getExpenseElementRemark_47());
            }
            else if (elementID == 48) {
                expense.setQuantity(expensesJson.getExpenseElement_48());
                expense.setDuration(expensesJson.getExpenseElementDuration_48());
                expense.setRate(expensesJson.getExpenseElementRate_48());
                expense.setSum(expensesJson.getExpenseElementSum_48());
                expense.setRemark(expensesJson.getExpenseElementRemark_48());
            }
            else if (elementID == 49) {
                expense.setQuantity(expensesJson.getExpenseElement_49());
                expense.setDuration(expensesJson.getExpenseElementDuration_49());
                expense.setRate(expensesJson.getExpenseElementRate_49());
                expense.setSum(expensesJson.getExpenseElementSum_49());
                expense.setRemark(expensesJson.getExpenseElementRemark_49());
            }
            else if (elementID == 50) {
                expense.setQuantity(expensesJson.getExpenseElement_50());
                expense.setDuration(expensesJson.getExpenseElementDuration_50());
                expense.setRate(expensesJson.getExpenseElementRate_50());
                expense.setSum(expensesJson.getExpenseElementSum_50());
                expense.setRemark(expensesJson.getExpenseElementRemark_50());
            }
            else if (elementID == 51) {
                expense.setQuantity(expensesJson.getExpenseElement_51());
                expense.setDuration(expensesJson.getExpenseElementDuration_51());
                expense.setRate(expensesJson.getExpenseElementRate_51());
                expense.setSum(expensesJson.getExpenseElementSum_51());
                expense.setRemark(expensesJson.getExpenseElementRemark_51());
            }
            else if (elementID == 52) {
                expense.setQuantity(expensesJson.getExpenseElement_52());
                expense.setDuration(expensesJson.getExpenseElementDuration_52());
                expense.setRate(expensesJson.getExpenseElementRate_52());
                expense.setSum(expensesJson.getExpenseElementSum_52());
                expense.setRemark(expensesJson.getExpenseElementRemark_52());
            }
            else if (elementID == 53) {
                expense.setQuantity(expensesJson.getExpenseElement_53());
                expense.setDuration(expensesJson.getExpenseElementDuration_53());
                expense.setRate(expensesJson.getExpenseElementRate_53());
                expense.setSum(expensesJson.getExpenseElementSum_53());
                expense.setRemark(expensesJson.getExpenseElementRemark_53());
            }
            else if (elementID == 54) {
                expense.setQuantity(expensesJson.getExpenseElement_54());
                expense.setDuration(expensesJson.getExpenseElementDuration_54());
                expense.setRate(expensesJson.getExpenseElementRate_54());
                expense.setSum(expensesJson.getExpenseElementSum_54());
                expense.setRemark(expensesJson.getExpenseElementRemark_54());
            }
            else if (elementID == 55) {
                expense.setQuantity(expensesJson.getExpenseElement_55());
                expense.setDuration(expensesJson.getExpenseElementDuration_55());
                expense.setRate(expensesJson.getExpenseElementRate_55());
                expense.setSum(expensesJson.getExpenseElementSum_55());
                expense.setRemark(expensesJson.getExpenseElementRemark_55());
            }
            else if (elementID == 56) {
                expense.setQuantity(expensesJson.getExpenseElement_56());
                expense.setDuration(expensesJson.getExpenseElementDuration_56());
                expense.setRate(expensesJson.getExpenseElementRate_56());
                expense.setSum(expensesJson.getExpenseElementSum_56());
                expense.setRemark(expensesJson.getExpenseElementRemark_56());
            }
            else if (elementID == 57) {
                expense.setQuantity(expensesJson.getExpenseElement_57());
                expense.setDuration(expensesJson.getExpenseElementDuration_57());
                expense.setRate(expensesJson.getExpenseElementRate_57());
                expense.setSum(expensesJson.getExpenseElementSum_57());
                expense.setRemark(expensesJson.getExpenseElementRemark_57());
            }
            else if (elementID == 58) {
                expense.setQuantity(expensesJson.getExpenseElement_58());
                expense.setDuration(expensesJson.getExpenseElementDuration_58());
                expense.setRate(expensesJson.getExpenseElementRate_58());
                expense.setSum(expensesJson.getExpenseElementSum_58());
                expense.setRemark(expensesJson.getExpenseElementRemark_58());
            }
            else if (elementID == 59) {
                expense.setQuantity(expensesJson.getExpenseElement_59());
                expense.setDuration(expensesJson.getExpenseElementDuration_59());
                expense.setRate(expensesJson.getExpenseElementRate_59());
                expense.setSum(expensesJson.getExpenseElementSum_59());
                expense.setRemark(expensesJson.getExpenseElementRemark_59());
            }
            else if (elementID == 60) {
                expense.setQuantity(expensesJson.getExpenseElement_60());
                expense.setDuration(expensesJson.getExpenseElementDuration_60());
                expense.setRate(expensesJson.getExpenseElementRate_60());
                expense.setSum(expensesJson.getExpenseElementSum_60());
                expense.setRemark(expensesJson.getExpenseElementRemark_60());
            }
            else {
                expense.setQuantity(expensesJson.getExpenseElement_61());
                expense.setDuration(expensesJson.getExpenseElementDuration_61());
                expense.setRate(expensesJson.getExpenseElementRate_61());
                expense.setSum(expensesJson.getExpenseElementSum_61());
                expense.setRemark(expensesJson.getExpenseElementRemark_61());
            }

            expensesService.save(expense);
        }



        return "quotation/expenses";
    }

}
