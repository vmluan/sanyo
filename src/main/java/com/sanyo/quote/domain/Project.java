package com.sanyo.quote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "project", catalog = "sanyo", uniqueConstraints = {
		@UniqueConstraint(columnNames = "PROJECT_NAME"),
		@UniqueConstraint(columnNames = "PROJECT_CODE") })
public class Project implements java.io.Serializable {

	private Integer projectId;
	private String projectCode;
	private String projectName;
	String customerName;
	
//	private Set<Region> regions; // TO be removed.
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
	
	private float impTax = 0f;
	private float specialTax = 0f;
	private float VAT = 0f;
	private float discountRate = 100f;
	private float allowance = 105f;
	private float subConProfit = 105f; //mặc định 105%, lấy từ bản thông số chung của dự án
	
	private String vndToUsd;
	private String usdToVnd;
	private String jpyToVnd;
	private String vndToJpy;
	private String usdToJpy;
	private String jpyToUsd;
	
	private Set<ProjectRevision> revisions;
	private Set<Location> locations;
	
	private float qtySubMain =105f;
	private float eQtyOther =105f;
	private float mQty =105f;
	
	private Currency currency;
	private Date lmodDate;
	private Date createdDate;
	
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date startDate;
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date endDate;
	
	private Set<ProductGroupMaker> productGroupMakers;
	
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
//	@JsonIgnore
//	@OneToMany(fetch=FetchType.EAGER, mappedBy="project")
//	public Set<Region> getRegions() {
//		return regions;
//	}
//
//	public void setRegions(Set<Region> regions) {
//		this.regions = regions;
//	}
	
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

	public float getImpTax() {
		return impTax;
	}

	public void setImpTax(float impTax) {
		this.impTax = impTax;
	}

	public float getSpecialTax() {
		return specialTax;
	}

	public void setSpecialTax(float specialTax) {
		this.specialTax = specialTax;
	}

	public float getVAT() {
		return VAT;
	}

	public void setVAT(float vAT) {
		VAT = vAT;
	}

	public float getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(float discountRate) {
		this.discountRate = discountRate;
	}

	public float getAllowance() {
		return allowance;
	}

	public void setAllowance(float allowance) {
		this.allowance = allowance;
	}


	public float getSubConProfit() {
		return subConProfit;
	}

	public void setSubConProfit(float subConProfit) {
		this.subConProfit = subConProfit;
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

	public float getQtySubMain() {
		return qtySubMain;
	}

	public void setQtySubMain(float qtySubMain) {
		this.qtySubMain = qtySubMain;
	}

	public float geteQtyOther() {
		return eQtyOther;
	}

	public void seteQtyOther(float eQtyOther) {
		this.eQtyOther = eQtyOther;
	}

	public float getmQty() {
		return mQty;
	}

	public void setmQty(float mQty) {
		this.mQty = mQty;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@JsonIgnore
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CURRENCY_ID", nullable = true)
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="project")
	public Set<ProductGroupMaker> getProductGroupMakers() {
		return productGroupMakers;
	}
	public void setProductGroupMakers(Set<ProductGroupMaker> productGroupMakers) {
		this.productGroupMakers = productGroupMakers;
	}
	
}
