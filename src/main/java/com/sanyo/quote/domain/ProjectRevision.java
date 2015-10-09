package com.sanyo.quote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "revision", catalog = "sanyo")
public class ProjectRevision implements java.io.Serializable{
	private Integer revisionId;
	private String date;
	private String revisionNo;
	private String revisionDesc;
	
	private Project project;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "REVISION_ID", unique = true, nullable = false)
	public Integer getRevisionId() {
		return revisionId;
	}
	public void setRevisionId(Integer revisionId) {
		this.revisionId = revisionId;
	}
	
	@Column(name = "DATE")
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	@Column(name = "REVISION_NO")
	public String getRevisionNo() {
		return revisionNo;
	}
	public void setRevisionNo(String revisionNo) {
		this.revisionNo = revisionNo;
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
	
	@Column(name = "REVISION_DESC")
	public String getRevisionDesc() {
		return revisionDesc;
	}
	public void setRevisionDesc(String revisionDesc) {
		this.revisionDesc = revisionDesc;
	}
	
	
	

}
