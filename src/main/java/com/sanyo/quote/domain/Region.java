package com.sanyo.quote.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

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
//	private Set<Group> groupList;
	private Set<Encounter> encounters;
	private RegionStatus status;
	private Set<User> users;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CATEGORY_ID", nullable = false)
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name="region_group", joinColumns={@JoinColumn(name="REGION_ID")}
//			, inverseJoinColumns={@JoinColumn(name="groupid")})
//	public Set<Group> getGroupList() {
//		return groupList;
//	}
//	public void setGroupList(Set<Group> groupList) {
//		this.groupList = groupList;
//	}
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="region")
	public Set<Encounter> getEncounters() {
		return encounters;
	}
	public void setEncounters(Set<Encounter> encounters) {
		this.encounters = encounters;
	}
	@Column(name = "STATUS")
	public RegionStatus getStatus() {
		return status;
	}
	public void setStatus(RegionStatus status) {
		this.status = status;
	}
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="region_user", joinColumns={@JoinColumn(name="REGION_ID")}
			, inverseJoinColumns={@JoinColumn(name="userid")})
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	
	
}
