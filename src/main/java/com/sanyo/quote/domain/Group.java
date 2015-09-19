package com.sanyo.quote.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "sy_group", catalog = "sanyo")
public class Group implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "groupid", nullable = false)
	private Integer groupid;
	
	@NotEmpty
	@Column(name = "groupname",unique = true)
	private String groupName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "loginadministrator")
	private Boolean loginadministrator;
	@Column(name = "viewuser")
	private Boolean viewuser;
	@Column(name = "edituser")
	private Boolean edituser;
	@Column(name = "adduser")
	private Boolean adduser;
	@Column(name = "viewgroup")
	private Boolean viewgroup;
	@Column(name = "editgroup")
	private Boolean editgroup;
	@Column(name = "addgroup")
	private Boolean addgroup;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "grouplist")
	private Set<User> users;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "groupList")
	private Set<Region> regions;
	
	public Set<Region> getRegions() {
		return regions;
	}

	public void setRegions(Set<Region> regions) {
		this.regions = regions;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getLoginadministrator() {
		return loginadministrator;
	}

	public void setLoginadministrator(Boolean loginadministrator) {
		this.loginadministrator = loginadministrator;
	}

	public Boolean getViewuser() {
		return viewuser;
	}

	public void setViewuser(Boolean viewuser) {
		this.viewuser = viewuser;
	}

	public Boolean getEdituser() {
		return edituser;
	}

	public void setEdituser(Boolean edituser) {
		this.edituser = edituser;
	}

	public Boolean getAdduser() {
		return adduser;
	}

	public void setAdduser(Boolean adduser) {
		this.adduser = adduser;
	}

	public Boolean getViewgroup() {
		return viewgroup;
	}

	public void setViewgroup(Boolean viewgroup) {
		this.viewgroup = viewgroup;
	}

	public Boolean getEditgroup() {
		return editgroup;
	}

	public void setEditgroup(Boolean editgroup) {
		this.editgroup = editgroup;
	}

	public Boolean getAddgroup() {
		return addgroup;
	}

	public void setAddgroup(Boolean addgroup) {
		this.addgroup = addgroup;
	}
}
