package com.sanyo.quote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(name = "project", catalog = "sanyo", uniqueConstraints = {
		@UniqueConstraint(columnNames = "PROJECT_NAME"),
		@UniqueConstraint(columnNames = "PROJECT_CODE") })
public class Project implements java.io.Serializable {

	private Integer projectId;
	private String projectCode;
	private String projectName;
	String customerName;
	
	private Set<Region> regions;
	private long totalMoney;
	private Date openTime;
	private Date closedTime;
	private ProjectStatus status;
	
	//use @Version to implement Optimistic Locking. When 2 or more concurrency update on same records. Exception will be raised
	// This helps to avoid losing data of previous update.
	@Version
	private long version;
	private String createdBy;
	private String description;
	private String lastModifiedBy;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "PROJECT_ID", unique = true, nullable = false)
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	@Column(name = "PROJECT_CODE", unique = true, nullable = false, length = 10)
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Column(name = "PROJECT_NAME", unique = true, nullable = false, length = 20)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Column(name = "CUST_NAME")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "TOTAL_MONEY")
	public long getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(long totalMoney) {
		this.totalMoney = totalMoney;
	}

	@Column(name = "OPEN_TIME")
	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	
	@Column(name = "CLOSED_TIME")
	public Date getClosedTime() {
		return closedTime;
	}

	public void setClosedTime(Date closedTime) {
		this.closedTime = closedTime;
	}

	@Column(name = "STATUS")
	public ProjectStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@OneToMany(fetch=FetchType.EAGER, mappedBy="project")
	public Set<Region> getRegions() {
		return regions;
	}

	public void setRegions(Set<Region> regions) {
		this.regions = regions;
	}
	
	@Column(name = "LAST_MODIFIED_BY")
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
}
