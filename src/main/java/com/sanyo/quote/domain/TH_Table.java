package com.sanyo.quote.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Synchronize;

@Entity
@Table(name = "sy_table", catalog = "sanyo")
public class TH_Table implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tableid", nullable = false)
	private Integer tableID;
	
	@Column(name = "customername")
	String customerName;
	
	//sample value is ban1201410191234
	@Index(name = "tableacr")
	private String tableAcr;
	
	//sample value is b1, b2. This is hidden field
	@Column(name = "tablenumber")
	private String tableNumber;
	
	@Column(name = "totalmoney")
	private long totalMoney;
	
	@Column(name = "opentime")
	private Date openTime;
	
	@Column(name = "closedtime")
	private Date closedTime;
	
	@Column(name = "status")
	private TH_TableStatus status;
	
	//sample value is ban 1, ban 2
	@Column(name = "tablename")
	private String tableName;
	//use @Version to implement Optimistic Locking. When 2 or more concurrency update on same records. Exception will be raised
	// This helps to avoid losing data of previous update.
	@Version
	private long version;
	
	
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableAcr() {
		return tableAcr;
	}

	public void setTableAcr(String tableAcr) {
		this.tableAcr = tableAcr;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "table")
	List<TH_Encounter> encounters; //

	public Integer getTableID() {
		return tableID;
	}

	public void setTableID(Integer tableID) {
		this.tableID = tableID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public List<TH_Encounter> getEncounters() {
		return encounters;
	}

	public void setEncounters(List<TH_Encounter> encounters) {
		this.encounters = encounters;
	}

	public String getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
	}
	public long getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(long totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getClosedTime() {
		return closedTime;
	}

	public void setClosedTime(Date closedTime) {
		this.closedTime = closedTime;
	}

	public TH_TableStatus getStatus() {
		return status;
	}

	public void setStatus(TH_TableStatus status) {
		this.status = status;
	}

}
