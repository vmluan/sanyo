package com.sanyo.quote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 * it presents the 'Vung' term. 
 * 
 */
@Entity
@Table(name = "region", catalog = "sanyo")
public class Region implements java.io.Serializable{
	private Integer regionId;
	private String regionName;
	private String regionDesc;
	private Category category;
	private Project project;
	private List<Group> groupList;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "REGION_ID", unique = true, nullable = false)
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	
	@Column(name = "REGION_NAME")
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	@Column(name = "REGION_DESC")
	public String getRegionDesc() {
		return regionDesc;
	}
	public void setRegionDesc(String regionDesc) {
		this.regionDesc = regionDesc;
	}
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CATEGORY_ID", nullable = false)
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="region_group", joinColumns={@JoinColumn(name="REGION_ID")}
			, inverseJoinColumns={@JoinColumn(name="groupid")})
	public List<Group> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}
	

}
