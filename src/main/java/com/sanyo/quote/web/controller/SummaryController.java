package com.sanyo.quote.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Chuong Thai on 10/8/2015.
 */

@Controller
@RequestMapping(value = "/summary")
public class SummaryController {

    @RequestMapping(method = RequestMethod.GET)
    public String getBOQPage(Model uiModel) {
        return "quotation/summary";
    }
}
