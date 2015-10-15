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

@Entity
@Table(name = "currency_exch_rate", catalog = "sanyo")
public class CurrecyExchRate {
	
	private Integer id;
	private Currency sourceCurrency;
	private Currency targetCurrency;
	private Float exchangeRateValue;
	private Date startDate;
	private Date endDate;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SOURCE_CURRENCY_ID", nullable = false)
	public Currency getSourceCurrency() {
		return sourceCurrency;
	}
	public void setSourceCurrency(Currency sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TARGET_CURRENCY_ID", nullable = false)
	public Currency getTargetCurrency() {
		return targetCurrency;
	}
	public void setTargetCurrency(Currency targetCurrency) {
		this.targetCurrency = targetCurrency;
	}
	public Float getExchangeRateValue() {
		return exchangeRateValue;
	}
	public void setExchangeRateValue(Float exchangeRateValue) {
		this.exchangeRateValue = exchangeRateValue;
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
}
