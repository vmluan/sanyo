package com.sanyo.quote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user", catalog = "sanyo")
public class ProjectRevision implements java.io.Serializable{
	private Integer revisionId;
	private String date;
	private String revisionNo;
	
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
	
	

}
