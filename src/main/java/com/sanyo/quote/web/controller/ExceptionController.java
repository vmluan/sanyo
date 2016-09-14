package com.sanyo.quote.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by luan on 9/14/16.
 */
@Controller
@RequestMapping("/exception")
public class ExceptionController {

    /*
        This method is to handle all exception raised in this application.
     */
    @RequestMapping(method = RequestMethod.GET, value = "error")
    public String handleAllError(Model uiModel, HttpServletRequest request) {
        return "exception/error";
    }
}
