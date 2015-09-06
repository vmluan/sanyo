package com.sanyo.quote.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanyo.quote.domain.TH_Table;
import com.sanyo.quote.domain.TH_TableStatus;

public interface TableService {

	List<TH_Table> findAll();
	List<TH_Table> findTableByDate(Date date);
	List<TH_Table> findTableByDate(Date date, TH_TableStatus status);
	List<TH_Table> findTableByDate(Date date, TH_TableStatus status1, TH_TableStatus status2);
	
	List<TH_Table> findTableBuyTableNumber(String tableNumber);
	List<TH_Table> findOpeningTableByTableNumber(String tableNumber);
	List<TH_Table> findTableByTableAcr(String tableAcr);
	TH_Table findById(Integer id);
	
	TH_Table save(TH_Table TH_Product);
	
	Page<TH_Table> findAllByPage(Pageable pageable);
	List<TH_Table> findTableByDateRange(Date startDate, Date endDate);
	
	
}
