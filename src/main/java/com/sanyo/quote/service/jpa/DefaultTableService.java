
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
import com.sanyo.quote.domain.TH_Table;
import com.sanyo.quote.domain.TH_TableStatus;
import com.sanyo.quote.repository.TableRepository;
import com.sanyo.quote.service.TableService;

@Service("tableService")
@Repository
@Transactional
public class DefaultTableService implements TableService {

	@Autowired
	private TableRepository tableRepository;

	@Transactional(readOnly=true)
	public List<TH_Table> findAll() {
		return Lists.newArrayList(tableRepository.findAll());
	}

	@Transactional(readOnly=true)
	public TH_Table findById(Integer id) {
		return tableRepository.findOne(id);
	}

	public TH_Table save(TH_Table th_table) {
		return tableRepository.save(th_table);
	}

	@Transactional(readOnly=true)
	public Page<TH_Table> findAllByPage(Pageable pageable) {
		return tableRepository.findAll(pageable);
	}

	@Override
	public List<TH_Table> findTableByDate(Date date) {
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
//		  List<TH_Table> tables = new ArrayList<TH_Table>();
//		  List<Object[]> rawData = tableRepository.findTableByDate(date, nextDate);
//		  for (Object[]data : rawData){
//			  TH_Table table = new TH_Table();
//			  table.setTableID(Integer.valueOf(String.valueOf(data[0])));
//			  table.setCustomerName(String.valueOf(data[1]));
//			  table.setTableAcr(String.valueOf(data[2]));
//			  table.setTableNumber(String.valueOf(data[3]));
//			  table.setTotalMoney(Long.valueOf(String.valueOf(data[4])));
//			  
//			  String status = String.valueOf(data[6]);
//			  if(status.equalsIgnoreCase(String.valueOf(TH_TableStatus.DRINKING)))
//			  	table.setStatus(TH_TableStatus.DRINKING);
//			  else if(status.equalsIgnoreCase(String.valueOf(TH_TableStatus.PAID)))
//				  table.setStatus(TH_TableStatus.PAID);
//			  else if(status.equalsIgnoreCase(String.valueOf(TH_TableStatus.CLOSED)))
//				  table.setStatus(TH_TableStatus.CLOSED);
//			  else if(status.equalsIgnoreCase(String.valueOf(TH_TableStatus.DEBT)))
//				  table.setStatus(TH_TableStatus.DEBT);
//			  		
//			  
//			  tables.add(table);
//			  
//		  }
//		return tables;
		  List<TH_Table> tables = new ArrayList<TH_Table>();
		  List<Object[]> rawData = tableRepository.findDistinctTableByDate(date, nextDate);
		  System.out.println(rawData);
		  
		  for (Object[]data : rawData){
				 String tableName = String.valueOf(data[0]);
				 List<TH_Table> temp = tableRepository.findTableByDate(date, nextDate, tableName);
				 if(temp != null && temp.size() > 0){
					 tables.add(temp.get(0));
				 }			  
		  }
		  return tables;
	}

	@Override
	public List<TH_Table> findTableByDate(Date date, TH_TableStatus status) {
		  final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		  final Calendar calendar = Calendar.getInstance();
		  calendar.setTime(date);
		  calendar.add(Calendar.DAY_OF_YEAR, 1);
		  Date nextDate =  calendar.getTime();
		  
		return tableRepository.findTableByDate(date, nextDate, status);
	}

	@Override
	public List<TH_Table> findTableByDate(Date date, TH_TableStatus status1,
			TH_TableStatus status2) {
		  final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		  final Calendar calendar = Calendar.getInstance();
		  calendar.setTime(date);
		  calendar.add(Calendar.DAY_OF_YEAR, 1);
		  Date nextDate =  calendar.getTime();
		  
		return tableRepository.findTableByDate(date, nextDate, status1, status2);
	}

	@Override
	public List<TH_Table> findTableBuyTableNumber(String tableNumber) {
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
		return tableRepository.findTableBuyTableNumber(tableNumber, date,
				nextDate);
	}
	@Override
	public List<TH_Table> findOpeningTableByTableNumber(String tableNumber) {
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
		return tableRepository.findTableByTableNumberAndStatus(tableNumber, date,
				nextDate, TH_TableStatus.DRINKING);
	}

	@Override
	public List<TH_Table> findTableByTableAcr(String tableAcr) {
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
		return tableRepository.findTableByTableAcrAndStatus(tableAcr, date,
				nextDate, TH_TableStatus.DRINKING);
	}

	@Override
	public List<TH_Table> findTableByDateRange(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return tableRepository.findTableByDate(startDate, endDate);
	}	
	
	
}
