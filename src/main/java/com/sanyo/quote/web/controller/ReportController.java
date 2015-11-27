package com.sanyo.quote.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sanyo.quote.domain.Project;
import com.sanyo.quote.helper.ReportExcel;
import com.sanyo.quote.service.CategoryService;
import com.sanyo.quote.service.EncounterService;
import com.sanyo.quote.service.LocationService;
import com.sanyo.quote.service.MakerProjectService;
import com.sanyo.quote.service.MakerService;
import com.sanyo.quote.service.ProductGroupMakerService;
import com.sanyo.quote.service.ProductGroupService;
import com.sanyo.quote.service.ProductService;
import com.sanyo.quote.service.ProjectRevisionService;
import com.sanyo.quote.service.ProjectService;
import com.sanyo.quote.service.RegionService;

@Controller
@RequestMapping(value = "/reports")
public class ReportController {
	final Logger logger = LoggerFactory.getLogger(ReportController.class);
	@Autowired
	private EncounterService encounterService;
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private MakerService makerService;
	
	@Autowired
	private ProductGroupService productGroupService;
	
	@Autowired
	private ProductGroupMakerService productGroupMakerService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MakerProjectService makerProjectService;
	@Autowired
	private ProjectRevisionService projectRevisionService;
	@RequestMapping(value = "/{id}/report", method = RequestMethod.GET)
	public void showRegions(@PathVariable("id") Integer id, Model uiModel, HttpServletRequest httpServletRequest, HttpServletResponse response){
		Project project = projectService.findById(id);
		if(project != null){
			response.setHeader("Content-disposition", "attachment; filename=" +project.getProjectName() +".xlsx");
			response.setContentType("application/vnd.ms-excel");
			ReportExcel reportExcel = new ReportExcel();
			reportExcel.setEncounterService(encounterService);
			reportExcel.setProjectService(projectService);
			reportExcel.setLocationService(locationService);
			reportExcel.setMakerProjectService(makerProjectService);
			reportExcel.setProjectRevisionService(projectRevisionService);
			XSSFWorkbook workbook =  reportExcel.writeExcelReportClientForProject(project, "template_quotaion_client.xlsx");
			try {
				workbook.write(response.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
