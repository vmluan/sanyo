package com.sanyo.quote.domain;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "condition2", catalog = "sanyo")
public class Condition2 implements Serializable, Cloneable{

	private Integer conditionid;	
	
	//private Integer projectid;	
	private Project project;
	
	private String checkboxs;	
	
	private String contents;
	
	

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "conditionid", unique = true, nullable = false)
	public Integer getConditionid() {
		return conditionid;
	}

	public void setConditionid(Integer conditionid) {
		this.conditionid = conditionid;
	}

	/*
	@NotEmpty
	@Column(name = "projectid",unique = true)
	public Integer getProjectid() {
		return projectid;
	}

	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}*/
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Column(name = "checkboxs", columnDefinition="TEXT")
	public String getCheckboxs() {
		return checkboxs;
	}

	public void setCheckboxs(String checkboxs) {
		this.checkboxs = checkboxs;
	}

	@Column(name = "contents", columnDefinition="TEXT")
	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	@Override
	public Condition2 clone() throws CloneNotSupportedException {
		Condition2 clonedObj = (Condition2) super.clone();
		clonedObj.setConditionid(null);
		clonedObj.setProject(null);
		return clonedObj;
	}
}
