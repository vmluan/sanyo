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
@Table(name = "sy_encounter", catalog = "sanyo")
public class TH_Encounter implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "encounterid", nullable = false)
	private Integer encounterID;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Product product;
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
	private Date encounterTime;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tableid", nullable = false)
	private TH_Table table;
	
	@Column(name = "productprice")
	private long productPrice;
		
	public long getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(long productPrice) {
		this.productPrice = productPrice;
	}

	public TH_Table getTable() {
		return table;
	}

	public void setTable(TH_Table table) {
		this.table = table;
	}

	public Integer getEncounterID() {
		return encounterID;
	}

	public void setEncounterID(Integer encounterID) {
		this.encounterID = encounterID;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getEncounterTime() {
		return encounterTime;
	}

	public void setEncounterTime(Date encounterTime) {
		this.encounterTime = encounterTime;
	}
	
	

}
