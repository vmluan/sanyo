package com.sanyo.quote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "summary", catalog = "sanyo")
public class Summary implements java.io.Serializable{
	private Integer summaryid;
	private Project project;
	private Integer siteexpenses;
	private Integer japanese;
	private Integer engineer;
	private Integer profit;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="SUMMARY_ID")	
	public Integer getSummaryid() {
		return summaryid;
	}
	public void setSummaryid(Integer summaryid) {
		this.summaryid = summaryid;
	}
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	@Column(name="SITEEXPENSES")
	public Integer getSiteexpenses() {
		return siteexpenses;
	}
	public void setSiteexpenses(Integer siteexpenses) {
		this.siteexpenses = siteexpenses;
	}
	@Column(name="JAPANESE")
	public Integer getJapanese() {
		return japanese;
	}
	public void setJapanese(Integer japanese) {
		this.japanese = japanese;
	}
	@Column(name="ENGINEER")
	public Integer getEngineer() {
		return engineer;
	}
	public void setEngineer(Integer engineer) {
		this.engineer = engineer;
	}
	@Column(name="PROFIT")
	public Integer getProfit() {
		return profit;
	}
	public void setProfit(Integer profit) {
		this.profit = profit;
	}
	
}
