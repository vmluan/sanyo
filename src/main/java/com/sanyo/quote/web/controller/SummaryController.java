package com.sanyo.quote.web.controller;

import com.sanyo.quote.domain.Maker;
import com.sanyo.quote.domain.ProductGroupRate;
import com.sanyo.quote.helper.Utilities;
import com.sanyo.quote.service.ProductGroupRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Chuong Thai on 10/8/2015.
 */

@Controller
@RequestMapping(value = "/summary")
public class SummaryController {
    final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    ProductGroupRateService productGroupRateService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getSummaryPage(@PathVariable("id") Integer projectId,Model uiModel) {
        uiModel.addAttribute("projectId", projectId);
        return "quotation/summary";
    }

    @RequestMapping(value = "/getProductGroupRateJson/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getProductGroupRateJson(@RequestParam(value="filterscount", required=false) String filterscount,
                                          @RequestParam(value="productGroudCode", required=false) String productGroupCode,
            @RequestParam(value="groupscount", required=false) String groupscount,
            @RequestParam(value="pagenum", required=false) Integer pagenum,
            @RequestParam(value="pagesize", required=false) Integer pagesize,
            @RequestParam(value="recordstartindex", required=false) Integer recordstartindex,
            @RequestParam(value="recordendindex", required=false) Integer recordendindex,
            @PathVariable("id") Integer projectId,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Model uiModel) {
        if (projectId>0) {
            List<ProductGroupRate> productGroupRates = productGroupRateService.findByProjectId(projectId);
            if (productGroupRates != null) {
                return Utilities.jSonSerialization(productGroupRates);
            }
        }
        List<ProductGroupRate> productGroupRates = productGroupRateService.findAll();
        String result = Utilities.jSonSerialization(productGroupRates);
        return result;
    }
}
