package com.sanyo.quote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currency", catalog = "sanyo")
public class Currency implements java.io.Serializable{
	private Integer currencyId;
	private String currencyName; //like USD, VND;
	private String currencyCode; //like USD, VND
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CURRENCY_ID", unique = true, nullable = false)
	public Integer getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}
	@Column(name = "CURRENCY_Name", unique = true, nullable = false)
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	@Column(name = "CURRENCY_CODE", unique = true, nullable = false)
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}


}
