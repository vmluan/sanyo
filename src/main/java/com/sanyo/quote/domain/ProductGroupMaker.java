package com.sanyo.quote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.eclipse.persistence.sessions.factories.ProjectClassGenerator;

/*
 * it represent an instance of the many to many relationship between ProductGroup and Marker
 * as we need extra columns so create new class for handling data easily.
 * 
 * 
 */
@Entity
@Table(name = "productgroup_Maker", catalog = "sanyo")
public class ProductGroupMaker implements java.io.Serializable, Cloneable {
	private Integer id;
	private String delivery;
	private String remark;
	private Date startDate;
	private Date endDate;
	private String createdBy;
	private String createdDate;
	private String lmodDate;
	private String lmodUser;
	private ProductGroup productGroup;
	private Maker maker;
	private String modelNo;
//	private Region region; // will be removed later.
	private Category category;
	private Project project;
	private String equivalent;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getLmodDate() {
		return lmodDate;
	}
	public void setLmodDate(String lmodDate) {
		this.lmodDate = lmodDate;
	}
	public String getLmodUser() {
		return lmodUser;
	}
	public void setLmodUser(String lmodUser) {
		this.lmodUser = lmodUser;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ProductGroup_id", nullable = false)
	public ProductGroup getProductGroup() {
		return productGroup;
	}
	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="Maker_id", nullable = false)
	public Maker getMaker() {
		return maker;
	}
	public void setMaker(Maker maker) {
		this.maker = maker;
	}
	public String getModelNo() {
		return modelNo;
	}
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
//	@JsonIgnore
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="region_id", nullable = false)
//	public Region getRegion() {
//		return region;
//	}
//	public void setRegion(Region region) {
//		this.region = region;
//	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="category_id", nullable = true)
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="project_id", nullable = true)
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getEquivalent() {
		return equivalent;
	}
	public void setEquivalent(String equivalent) {
		this.equivalent = equivalent;
	}
	
	@Override
	public ProductGroupMaker clone() throws CloneNotSupportedException {
		ProductGroupMaker clonedObject = new ProductGroupMaker();
		clonedObject = (ProductGroupMaker) super.clone();
		return clonedObject;
	}
	
}
