package com.sanyo.quote.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Encounter;
import com.sanyo.quote.domain.Expenses;
import com.sanyo.quote.domain.Location;
import com.sanyo.quote.domain.ProductGroupMaker;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.ProjectStatus;

public interface ProjectService {

	List<Project> findAll();
	List<Project> findTableByDate(Date date);
	List<Project> findTableByDate(Date date, ProjectStatus status);
	List<Project> findTableByDate(Date date, ProjectStatus status1, ProjectStatus status2);
	
	List<Project> findTableBuyTableNumber(String tableNumber);
	List<Project> findOpeningTableByTableNumber(String tableNumber);
	List<Project> findTableByTableAcr(String tableAcr);
	Project findById(Integer id);
	
	Project save(Project project);
	
	Page<Project> findAllByPage(Pageable pageable);
	List<Project> findTableByDateRange(Date startDate, Date endDate);
	
	Project findByIdAndFetchLocationsEagerly(Integer id);
	
//	Project findByIdAndFetchMakers(Integer id);
	
	List<Project> findProjectsWithStatus(ProjectStatus projectStatus);
	
	List<Location> findLocations(Integer id);
	
//	List<ProductGroupMaker> findProductGroupMakers(Integer id);
	
	List<Expenses> findExpenses(Integer id);
	
	void delete(Integer id);
	void delte(Project project);
	List<Project> findByStatus(ProjectStatus status);
}
