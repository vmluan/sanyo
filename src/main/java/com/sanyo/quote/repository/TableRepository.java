package com.sanyo.quote.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Product;
import com.sanyo.quote.domain.TH_Table;
import com.sanyo.quote.domain.TH_TableStatus;


public interface TableRepository extends PagingAndSortingRepository<TH_Table, Integer> {
	
//	@Query("select c.tableID, c.customerName, c.tableAcr, c.tableNumber, sum(c.totalMoney), c.openTime, c.status"
//			+ " from TH_Table c where c.openTime  between :tradeDate and :nextTradeDate group by c.tableAcr order by c.status asc, c.openTime desc ")
//	List<Object[]> findTableByDate(@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate );
	
	
//sample query to get list of tables in order of table status
/*
				select a.* from coffee.th_table a,
				(select tablenumber, max(tableid) id from coffee.th_table
				where opentime between '2014-11-15' and '2014-11-16'
				#and tablenumber = 'Ban 1'
				group by tablenumber) b
				where a.tableid = b.id;
				
				select c from TH_Table c, (select a.tablNnumber, max(a.tableID) id from TH_Table a
				where where a.openTime  between :tradeDate and :nextTradeDate group by a.tableNumber) b
				where c.tableID =: a.id 
 * 
 */
	
	@Query("select c"
			+ " from TH_Table c where c.openTime  between :tradeDate and :nextTradeDate and c.tableNumber =:tableNumber  order by c.status asc, c.openTime desc limit 1")
	List<TH_Table> findTableByDate(@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate,  @Param("tableNumber") String tableNumber );
	
	@Query("select c from TH_Table c where c.openTime  between :tradeDate and :nextTradeDate and c.status = :status order by c.status asc, c.openTime desc")
	List<TH_Table> findTableByDate(@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate, 
			@Param("status") TH_TableStatus status);
	@Query("select c from TH_Table c where c.openTime  between :tradeDate and :nextTradeDate and (c.status = :status1 or c.status =:status2) order by c.status asc, c.openTime desc")
	List<TH_Table> findTableByDate(@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate, 
			@Param("status1") TH_TableStatus status1, @Param("status2") TH_TableStatus status2);
	
	@Query("select c from TH_Table c where c.openTime  between :tradeDate and :nextTradeDate and c.tableNumber = :tableNumber order by  c.status asc, c.openTime desc")
	List<TH_Table> findTableBuyTableNumber(@Param("tableNumber") String tableNumber,@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate);
	
	@Query("select c from TH_Table c where c.openTime  between :tradeDate and :nextTradeDate and c.tableNumber = :tableNumber and c.status = :status order by c.openTime desc")
	List<TH_Table> findTableByTableNumberAndStatus(@Param("tableNumber") String tableNumber,@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate
			, @Param("status") TH_TableStatus status);
		
	@Query("select c from TH_Table c where c.openTime  between :tradeDate and :nextTradeDate and c.tableAcr = :tableAcr and c.status = :status order by c.openTime desc")
	List<TH_Table> findTableByTableAcrAndStatus(@Param("tableAcr") String tableAcr,@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate
			, @Param("status") TH_TableStatus status);

	
	@Query("select c.tableNumber, max(tableID)"
			+ " from TH_Table c where c.openTime  between :tradeDate and :nextTradeDate  group by c.tableNumber")	
	List<Object[]>  findDistinctTableByDate(@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate);
	@Query("select c"
			+ " from TH_Table c where c.openTime  between :tradeDate and :nextTradeDate  order by c.status asc, c.openTime desc")
	List<TH_Table> findTableByDate(@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate );	
}
