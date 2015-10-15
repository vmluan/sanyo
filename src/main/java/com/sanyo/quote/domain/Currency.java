package com.sanyo.quote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currency", catalog = "sanyo")
public class Currency {
	private Integer currencyId;
	private String currencyName; //like USD, VND;
	private String currecnyCode; //like USD, VND
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CURRENCY_ID", unique = true, nullable = false)
	public Integer getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getCurrecnyCode() {
		return currecnyCode;
	}
	public void setCurrecnyCode(String currecnyCode) {
		this.currecnyCode = currecnyCode;
	}


}
