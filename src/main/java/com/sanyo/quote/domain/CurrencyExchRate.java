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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "currency_exch_rate", catalog = "sanyo")
public class CurrencyExchRate implements java.io.Serializable {
	
	private Integer id;
	private Currency sourceCurrency;
	private Currency targetCurrency;
	private Float exchangeRateValue;
	
	private Date startDate;
	private Date endDate;
	private String sourceCurrencyId;
	private String targetCurrencyId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="SOURCE_CURRENCY_ID", nullable = false)
	public Currency getSourceCurrency() {
		return sourceCurrency;
	}
	public void setSourceCurrency(Currency sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="TARGET_CURRENCY_ID", nullable = false)
	public Currency getTargetCurrency() {
		return targetCurrency;
	}
	public void setTargetCurrency(Currency targetCurrency) {
		this.targetCurrency = targetCurrency;
	}
	@NotNull
	public Float getExchangeRateValue() {
		return exchangeRateValue;
	}
	public void setExchangeRateValue(Float exchangeRateValue) {
		this.exchangeRateValue = exchangeRateValue;
	}
	@NotNull
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Transient
	public String getSourceCurrencyId() {
		return sourceCurrencyId;
	}
	public void setSourceCurrencyId(String sourceCurrencyId) {
		this.sourceCurrencyId = sourceCurrencyId;
	}
	@Transient
	public String getTargetCurrencyId() {
		return targetCurrencyId;
	}
	public void setTargetCurrencyId(String targetCurrencyId) {
		this.targetCurrencyId = targetCurrencyId;
	}
	
}
