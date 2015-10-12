
package com.sanyo.quote.service.jpa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.ProjectStatus;
import com.sanyo.quote.repository.ProjectRepository;
import com.sanyo.quote.service.ProjectService;

@Service("projectService")
@Repository
@Transactional
public class DefaultProjectService implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Transactional(readOnly=true)
	public List<Project> findAll() {
		return Lists.newArrayList(projectRepository.findAll());
	}

	@Transactional(readOnly=true)
	public Project findById(Integer id) {
		return projectRepository.findOne(id);
	}

	public Project save(Project Project) {
		return projectRepository.save(Project);
	}

	@Transactional(readOnly=true)
	public Page<Project> findAllByPage(Pageable pageable) {
		return projectRepository.findAll(pageable);
	}

	@Override
	public List<Project> findTableByDate(Date date) {
		  final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		  final Calendar calendar = Calendar.getInstance();
		  
		  try {
			date = format.parse(format.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  calendar.setTime(date);
		  calendar.add(Calendar.DAY_OF_YEAR, 1);
		  Date nextDate =  calendar.getTime();
//		  List<Project> tables = new ArrayList<Project>();
//		  List<Object[]> rawData = projectRepository.findTableByDate(date, nextDate);
//		  for (Object[]data : rawData){
//			  Project table = new Project();
//			  table.setTableID(Integer.valueOf(String.valueOf(data[0])));
//			  table.setCustomerName(String.valueOf(data[1]));
//			  table.setTableAcr(String.valueOf(data[2]));
//			  table.setTableNumber(String.valueOf(data[3]));
//			  table.setTotalMoney(Long.valueOf(String.valueOf(data[4])));
//			  
//			  String status = String.valueOf(data[6]);
//			  if(status.equalsIgnoreCase(String.valueOf(ProjectStatus.PROCESSING)))
//			  	table.setStatus(ProjectStatus.PROCESSING);
//			  else if(status.equalsIgnoreCase(String.valueOf(ProjectStatus.PAID)))
//				  table.setStatus(ProjectStatus.PAID);
//			  else if(status.equalsIgnoreCase(String.valueOf(ProjectStatus.CLOSED)))
//				  table.setStatus(ProjectStatus.CLOSED);
//			  else if(status.equalsIgnoreCase(String.valueOf(ProjectStatus.DEBT)))
//				  table.setStatus(ProjectStatus.DEBT);
//			  		
//			  
//			  tables.add(table);
//			  
//		  }
//		return tables;
//		  List<Project> tables = new ArrayList<Project>();
//		  List<Object[]> rawData = projectRepository.findDistinctTableByDate(date, nextDate);
//		  System.out.println(rawData);
//		  
//		  for (Object[]data : rawData){
//				 String tableName = String.valueOf(data[0]);
//				 List<Project> temp = projectRepository.findTableByDate(date, nextDate, tableName);
//				 if(temp != null && temp.size() > 0){
//					 tables.add(temp.get(0));
//				 }			  
//		  }
//		  return tables;
		  return null;
	}

	@Override
	public List<Project> findTableByDate(Date date, ProjectStatus status) {
		  final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		  final Calendar calendar = Calendar.getInstance();
		  calendar.setTime(date);
		  calendar.add(Calendar.DAY_OF_YEAR, 1);
		  Date nextDate =  calendar.getTime();
		  
//		return projectRepository.findTableByDate(date, nextDate, status);
		  return null;
	}

	@Override
	public List<Project> findTableByDate(Date date, ProjectStatus status1,
			ProjectStatus status2) {
		  final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		  final Calendar calendar = Calendar.getInstance();
		  calendar.setTime(date);
		  calendar.add(Calendar.DAY_OF_YEAR, 1);
		  Date nextDate =  calendar.getTime();
		  
//		return projectRepository.findTableByDate(date, nextDate, status1, status2);
		  return null;
	}

	@Override
	public List<Project> findTableBuyTableNumber(String tableNumber) {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		String dateString = format.format(new Date());
		Date date = new Date();
		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date nextDate = calendar.getTime();
//		return projectRepository.findTableBuyTableNumber(tableNumber, date,
//				nextDate);
		return null;
	}
	@Override
	public List<Project> findOpeningTableByTableNumber(String tableNumber) {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		String dateString = format.format(new Date());
		Date date = new Date();
		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date nextDate = calendar.getTime();
//		return projectRepository.findTableByTableNumberAndStatus(tableNumber, date,
//				nextDate, ProjectStatus.PROCESSING);
		return null;
	}

	@Override
	public List<Project> findTableByTableAcr(String tableAcr) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		String dateString = format.format(new Date());
		Date date = new Date();
		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date nextDate = calendar.getTime();
//		return projectRepository.findTableByTableAcrAndStatus(tableAcr, date,
//				nextDate, ProjectStatus.PROCESSING);
		return null;
	}

	@Override
	public List<Project> findTableByDateRange(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
//		return projectRepository.findTableByDate(startDate, endDate);
		return null;
	}

	@Override
	public Project findByIdAndFetchLocationsEagerly(Integer id) {
		return projectRepository.findByIdAndFetchLocationsEagerly(id);
	}	
	
	
}
