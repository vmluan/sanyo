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
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

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
	
	private String duration;
	private String revisionNo;
	private String consStartDate;
	private String consEndDate;
	private String consDuration;
	private String exVnd;
	private String exJpy;
	private String exUsd;
	
	private String impTax;
	private String specialTax;
	private String VAT;
	private String discountRate;
	private String allowance;
	private float Subcon_Profit; //mặc định 105%, lấy từ bản thông số chung của dự án
	
	private String vndToUsd;
	private String usdToVnd;
	private String jpyToVnd;
	private String vndToJpy;
	private String usdToJpy;
	private String jpyToUsd;
	
	private Set<ProjectRevision> revisions;
	private Set<Location> locations;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "PROJECT_ID", unique = true, nullable = false)
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	@NotEmpty
	@NotNull
	@Column(name = "PROJECT_CODE", unique = true, nullable = false, length = 10)
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@NotEmpty
	@NotNull
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
	@JsonIgnore
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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getRevisionNo() {
		return revisionNo;
	}

	public void setRevisionNo(String revisionNo) {
		this.revisionNo = revisionNo;
	}

	public String getConsStartDate() {
		return consStartDate;
	}

	public void setConsStartDate(String consStartDate) {
		this.consStartDate = consStartDate;
	}

	public String getConsEndDate() {
		return consEndDate;
	}

	public void setConsEndDate(String consEndDate) {
		this.consEndDate = consEndDate;
	}

	public String getConsDuration() {
		return consDuration;
	}

	public void setConsDuration(String consDuration) {
		this.consDuration = consDuration;
	}

	public String getExVnd() {
		return exVnd;
	}

	public void setExVnd(String exVnd) {
		this.exVnd = exVnd;
	}

	public String getExJpy() {
		return exJpy;
	}

	public void setExJpy(String exJpy) {
		this.exJpy = exJpy;
	}

	public String getExUsd() {
		return exUsd;
	}

	public void setExUsd(String exUsd) {
		this.exUsd = exUsd;
	}

	public String getImpTax() {
		return impTax;
	}

	public void setImpTax(String impTax) {
		this.impTax = impTax;
	}

	public String getSpecialTax() {
		return specialTax;
	}

	public void setSpecialTax(String specialTax) {
		this.specialTax = specialTax;
	}

	public String getVAT() {
		return VAT;
	}

	public void setVAT(String vAT) {
		VAT = vAT;
	}

	public String getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}

	public String getAllowance() {
		return allowance;
	}

	public void setAllowance(String allowance) {
		this.allowance = allowance;
	}

	public float getSubcon_Profit() {
		return Subcon_Profit;
	}

	public void setSubcon_Profit(float subcon_Profit) {
		Subcon_Profit = subcon_Profit;
	}

	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="project")
	public Set<ProjectRevision> getRevisions() {
		return revisions;
	}

	public void setRevisions(Set<ProjectRevision> revisions) {
		this.revisions = revisions;
	}

	public String getVndToUsd() {
		return vndToUsd;
	}

	public void setVndToUsd(String vndToUsd) {
		this.vndToUsd = vndToUsd;
	}

	public String getUsdToVnd() {
		return usdToVnd;
	}

	public void setUsdToVnd(String usdToVnd) {
		this.usdToVnd = usdToVnd;
	}

	public String getJpyToVnd() {
		return jpyToVnd;
	}

	public void setJpyToVnd(String jpyToVnd) {
		this.jpyToVnd = jpyToVnd;
	}

	public String getVndToJpy() {
		return vndToJpy;
	}

	public void setVndToJpy(String vndToJpy) {
		this.vndToJpy = vndToJpy;
	}

	public String getUsdToJpy() {
		return usdToJpy;
	}

	public void setUsdToJpy(String usdToJpy) {
		this.usdToJpy = usdToJpy;
	}

	public String getJpyToUsd() {
		return jpyToUsd;
	}

	public void setJpyToUsd(String jpyToUsd) {
		this.jpyToUsd = jpyToUsd;
	}
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="project")
	public Set<Location> getLocations() {
		return locations;
	}

	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}
	
	
}
