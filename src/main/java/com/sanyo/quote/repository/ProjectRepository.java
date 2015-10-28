	package com.sanyo.quote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.sanyo.quote.domain.Expenses;
import com.sanyo.quote.domain.Location;
import com.sanyo.quote.domain.ProductGroupMaker;
import com.sanyo.quote.domain.Project;
import com.sanyo.quote.domain.ProjectStatus;


public interface ProjectRepository extends PagingAndSortingRepository<Project, Integer> {
	
//	@Query("select c.tableID, c.customerName, c.tableAcr, c.tableNumber, sum(c.totalMoney), c.openTime, c.status"
//			+ " from Project c where c.openTime  between :tradeDate and :nextTradeDate group by c.tableAcr order by c.status asc, c.openTime desc ")
//	List<Object[]> findTableByDate(@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate );
	
	
//sample query to get list of tables in order of table status
/*
				select a.* from coffee.Project a,
				(select tablenumber, max(tableid) id from coffee.Project
				where opentime between '2014-11-15' and '2014-11-16'
				#and tablenumber = 'Ban 1'
				group by tablenumber) b
				where a.tableid = b.id;
				
				select c from Project c, (select a.tablNnumber, max(a.tableID) id from Project a
				where where a.openTime  between :tradeDate and :nextTradeDate group by a.tableNumber) b
				where c.tableID =: a.id 
 * 
 */
//	
//	@Query("select c"
//			+ " from Project c where c.openTime  between :tradeDate and :nextTradeDate and c.tableNumber =:tableNumber  order by c.status asc, c.openTime desc limit 1")
//	List<Project> findTableByDate(@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate,  @Param("tableNumber") String tableNumber );
//	
//	@Query("select c from Project c where c.openTime  between :tradeDate and :nextTradeDate and c.status = :status order by c.status asc, c.openTime desc")
//	List<Project> findTableByDate(@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate, 
//			@Param("status") ProjectStatus status);
//	@Query("select c from Project c where c.openTime  between :tradeDate and :nextTradeDate and (c.status = :status1 or c.status =:status2) order by c.status asc, c.openTime desc")
//	List<Project> findTableByDate(@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate, 
//			@Param("status1") ProjectStatus status1, @Param("status2") ProjectStatus status2);
//	
//	@Query("select c from Project c where c.openTime  between :tradeDate and :nextTradeDate and c.tableNumber = :tableNumber order by  c.status asc, c.openTime desc")
//	List<Project> findTableBuyTableNumber(@Param("tableNumber") String tableNumber,@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate);
//	
//	@Query("select c from Project c where c.openTime  between :tradeDate and :nextTradeDate and c.tableNumber = :tableNumber and c.status = :status order by c.openTime desc")
//	List<Project> findTableByTableNumberAndStatus(@Param("tableNumber") String tableNumber,@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate
//			, @Param("status") ProjectStatus status);
//		
//	@Query("select c from Project c where c.openTime  between :tradeDate and :nextTradeDate and c.tableAcr = :tableAcr and c.status = :status order by c.openTime desc")
//	List<Project> findTableByTableAcrAndStatus(@Param("tableAcr") String tableAcr,@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate
//			, @Param("status") ProjectStatus status);
//
//	
//	@Query("select c.tableNumber, max(tableID)"
//			+ " from Project c where c.openTime  between :tradeDate and :nextTradeDate  group by c.tableNumber")	
//	List<Object[]>  findDistinctTableByDate(@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate);
//	@Query("select c"
//			+ " from Project c where c.openTime  between :tradeDate and :nextTradeDate  order by c.status asc, c.openTime desc")
//	List<Project> findTableByDate(@Param("tradeDate") Date tradeDate, @Param("nextTradeDate") Date nextTradeDate );
	
	@Query("SELECT p FROM Project p JOIN FETCH p.locations WHERE p.projectId = :id")
	public Project findByIdAndFetchLocationsEagerly(@Param("id") Integer id);
	
	@Query("SELECT p FROM Project p JOIN FETCH p.productGroupMakers WHERE p.projectId = :id")
	public Project findByIdAndFetchMakers(@Param("id") Integer id);

	@Query("SELECT p FROM Project p JOIN FETCH p.expenses WHERE p.projectId = :id")
	public Project findByIdAndFetchExpenses(@Param("id") Integer id);
	
	@Query("SELECT p FROM Project p WHERE p.status = :status")
	public List<Project> findProjectsWithStatus(@Param("status") ProjectStatus status);
	
	@Query("SELECT distinct  p.locations FROM Project p JOIN p.locations WHERE p.projectId = :id")
	public List<Location> findLocations(@Param("id") Integer id);
	
	@Query("SELECT distinct  p.productGroupMakers FROM Project p JOIN p.productGroupMakers WHERE p.projectId = :id")
	public List<ProductGroupMaker> findProductGroupMakers(@Param("id") Integer id);
	
	@Query("SELECT distinct  p.expenses FROM Project p JOIN p.expenses WHERE p.projectId = :id")
	public List<Expenses> findExpenses(@Param("id") Integer id);
	
	@Query("SELECT p FROM Project p WHERE p.status = :status")
	public List<Project> findByStatus(@Param("status") ProjectStatus status);
	
	
}
