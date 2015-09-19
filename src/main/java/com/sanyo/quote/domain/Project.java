package com.sanyo.quote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
//	private Set<ProjectCategory> projectCategories = new HashSet<ProjectCategory>(0);
	@Column(name = "customerName")
	String customerName;
	
	
	@Column(name = "totalMoney")
	private long totalMoney;
	
	@Column(name = "openTime")
	private Date openTime;
	
	@Column(name = "closedTime")
	private Date closedTime;
	
	@Column(name = "status")
	private ProjectStatus status;
	
	//use @Version to implement Optimistic Locking. When 2 or more concurrency update on same records. Exception will be raised
	// This helps to avoid losing data of previous update.
	@Version
	private long version;
	
	@JoinColumn(name = "userid")
	private String createdBy;
	
	@Column(name = "description")
	private String description;

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
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.project", cascade=CascadeType.ALL)
//	public Set<ProjectCategory> getProjectCategories() {
//		return projectCategories;
//	}
//
//	public void setProjectCategories(Set<ProjectCategory> projectCategories) {
//		this.projectCategories = projectCategories;
//	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
