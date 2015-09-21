package com.sanyo.quote.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "encounter", catalog = "sanyo")
public class Encounter implements Serializable{
	
	private Integer encounterID;
	private Product product;
	private int quantity;
	private Date encounterTime;
	private long productPrice;
	private int discountPercent;
	private short VAT;
	private long extraDiscount;
	private long originalMoney;
	private long totalMoney;
	private Region region;
	private EncounterStatus status;
	
	
	@Column(name = "PRODUCT_PRICE")
	public long getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(long productPrice) {
		this.productPrice = productPrice;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ENCOUNTER_ID", nullable = false)
	public Integer getEncounterID() {
		return encounterID;
	}
	public void setEncounterID(Integer encounterID) {
		this.encounterID = encounterID;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	@Column(name = "QUANTITY", nullable = false)
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Column(name = "CREATED_TIME")
	public Date getEncounterTime() {
		return encounterTime;
	}

	public void setEncounterTime(Date encounterTime) {
		this.encounterTime = encounterTime;
	}
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "REGION_ID", nullable = false)
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	@Column(name = "DISCOUNT_PER")
	public int getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
	}
	@Column(name = "VAT", nullable = false)
	public short getVAT() {
		return VAT;
	}

	public void setVAT(short vAT) {
		VAT = vAT;
	}
	@Column(name = "EXTRA_DISCOUNT")
	public long getExtraDiscount() {
		return extraDiscount;
	}

	public void setExtraDiscount(long extraDiscount) {
		this.extraDiscount = extraDiscount;
	}
	@Column(name = "ORIGINAL_MONEY", nullable = false)
	public long getOriginalMoney() {
		return originalMoney;
	}

	public void setOriginalMoney(long originalMoney) {
		this.originalMoney = originalMoney;
	}
	@Column(name = "TOTAL_MONEY", nullable = false)
	public long getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(long totalMoney) {
		this.totalMoney = totalMoney;
	}
	@Column(name = "STATUS")
	public EncounterStatus getStatus() {
		return status;
	}
	public void setStatus(EncounterStatus status) {
		this.status = status;
	}
	
	
	

}
