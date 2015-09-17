package com.sanyo.quote.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Index;

@Entity
@Table(name = "sy_project", catalog = "sanyo")
public class Project implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "projectId", nullable = false)
	private Integer projectId;
	
	@Column(name = "customerName")
	String customerName;
	
	@Index(name = "projectCode")
	private String projectCode;
	
	@Column(name = "projectName")
	private String projectName;
	
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.project", cascade=CascadeType.ALL)
	private List<ProjectCategory> projectCategories;
	
	
	
	public List<ProjectCategory> getProjectCategories() {
		return projectCategories;
	}

	public void setProjectCategories(List<ProjectCategory> projectCategories) {
		this.projectCategories = projectCategories;
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

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}


	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String tableAcr) {
		this.projectCode = tableAcr;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
	List<Encounter> encounters; //

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer tableID) {
		this.projectId = tableID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public List<Encounter> getEncounters() {
		return encounters;
	}

	public void setEncounters(List<Encounter> encounters) {
		this.encounters = encounters;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String tableNumber) {
		this.projectName = tableNumber;
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

}
