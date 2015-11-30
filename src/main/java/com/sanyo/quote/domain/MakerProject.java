package com.sanyo.quote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 * This class is for relatation between Project and ProductGroupMaker
 * 
 * We should have data for ProductGroup first, then Maker, then have relation between ProductGroup and Maker in ProductGroupMaker class
 * We finally have data in this table.
 */
@Entity
@Table(name = "maker_project", catalog = "sanyo")
public class MakerProject {
	private Integer id;
	private Project project;
	private ProductGroupMaker productGroupMaker;
	private String createdUser;
	private Date createdDate;
	private Date lmodDate;
	private String lmodUser;
	
	private String delivery;
	private String remark;
	private String createdBy;
	private String modelNo;
	private String equivalent;
	private Category category; // it is system on the GUI
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="project_id", nullable = false)
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="product_group_maker_id", nullable = false)
	public ProductGroupMaker getProductGroupMaker() {
		return productGroupMaker;
	}
	public void setProductGroupMaker(ProductGroupMaker productGroupMaker) {
		this.productGroupMaker = productGroupMaker;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLmodDate() {
		return lmodDate;
	}
	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
	}
	public String getLmodUser() {
		return lmodUser;
	}
	public void setLmodUser(String lmodUser) {
		this.lmodUser = lmodUser;
	}
	public String getDelivery() {
		return delivery;
	}
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModelNo() {
		return modelNo;
	}
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	public String getEquivalent() {
		return equivalent;
	}
	public void setEquivalent(String equivalent) {
		this.equivalent = equivalent;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="category_id", nullable = true)
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
}
