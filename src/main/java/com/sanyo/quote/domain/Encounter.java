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

	@Column(name="ENCOUNTER_ID")
	private Integer encounterID;

	@Column(name="PRODUCT")
	private Product product; //"Description", "productCode", "Unit" will be showed on the UI from

	@Column(name="ORDER")
	private int order; //No. STT dung de sap xep cac hang muc

	@Column(name="UNIT_RATE")
	private float unitRate; //Gia mac dinh lay theo USD

	@Column(name="QUANTITY")
	private float quantity; //So luong, khoi luong

	@Column(name="ACTUAL_QUANTITY")
	private float actualQuantity; //So luong, khoi luong thuc te = quantity * hao hut % ben sheet Summary (mac dinh)

	@Column(name="AMOUNT")
	private float amount; // = actualQuantity * unitRate. Don vi tinh theo USD
	

	@Column(name="REMARK")
	private String remark;

	@Column(name="TAX_USD")
	private float Mat_w_o_Tax_USD; //Mat w/o Tax USD

	@Column(name="TAX_VND")
	private float Mat_w_o_Tax_VND; //Mat w/o Tax VND

	@Column(name="LABOUR")
	private float labour; //lấy giá trị trong database phần nhân công từng hạng mục.
	//Theo thiết kế thì lấy từ bảng price với PriceType là LABOUR

	@Column(name="Imp_Tax")
	private float Imp_Tax; //Imp Tax. mặc định ="0%", lấy từ bản thông số chung của dự án

	@Column(name="Special_Con_Tax")
	private float Special_Con_Tax; // Special con. Tax. mặc định ="0%", lấy từ bản thông số chung của dự án

	@Column(name="Discount_rate")
	private float Discount_rate; //Discount rate %. mặc định ="100%", lấy từ bản thông số chung của dự án

	@Column(name="ENCOUNTER_TIME")
	private Date encounterTime;

	@Column(name="VAT")
	private short VAT; // mặc định ="0%", lấy từ bản thông số chung của dự án

	@Column(name="Unit_Price_After_Discount")
	private float Unit_Price_After_Discount; // = Mat_w_o_Tax_USD + (Mat_w_o_Tax_VND/Ti_gia_VND_to_USD)

	@Column(name="ALLOWANCE")
	private float allowance; //mặc định 105%, lấy từ bản thông số chung của dự án

	@Column(name="Unit_Price_W_Tax_Profit")
	private float Unit_Price_W_Tax_Profit; //Unit_Price_After_Discount * (1+(1+Special_Con_Tax*(1+Imp_Tax))*VAT)*Discount_rate

	@Column(name="Subcon_Profit")
	private float Subcon_Profit; //mặc định 105%, lấy từ bản thông số chung của dự án

	@Column(name="Unit_Price_W_Tax_Labour")
	private float Unit_Price_W_Tax_Labour; //=labour*Subcon_Profit

	@Column(name="Cost_Mat_Amount_USD")
	private float Cost_Mat_Amount_USD; // = Unit_Price_After_Discount * quantity

	@Column(name="Cost_Labour_Amount_USD")
	private float Cost_Labour_Amount_USD; //=Unit_Price_W_Tax_Labour* quantity


	private Region region;

	private EncounterStatus status;

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


	@Column(name = "STATUS")
	public EncounterStatus getStatus() {
		return status;
	}
	public void setStatus(EncounterStatus status) {
		this.status = status;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public float getUnitRate() {
		return unitRate;
	}

	public void setUnitRate(float unitRate) {
		this.unitRate = unitRate;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public float getActualQuantity() {
		return actualQuantity;
	}

	public void setActualQuantity(float actualQuantity) {
		this.actualQuantity = actualQuantity;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public float getMat_w_o_Tax_USD() {
		return Mat_w_o_Tax_USD;
	}

	public void setMat_w_o_Tax_USD(float mat_w_o_Tax_USD) {
		Mat_w_o_Tax_USD = mat_w_o_Tax_USD;
	}

	public float getMat_w_o_Tax_VND() {
		return Mat_w_o_Tax_VND;
	}

	public void setMat_w_o_Tax_VND(float mat_w_o_Tax_VND) {
		Mat_w_o_Tax_VND = mat_w_o_Tax_VND;
	}

	public float getLabour() {
		return labour;
	}

	public void setLabour(float labour) {
		this.labour = labour;
	}

	public float getImp_Tax() {
		return Imp_Tax;
	}

	public void setImp_Tax(float imp_Tax) {
		Imp_Tax = imp_Tax;
	}

	public float getSpecial_Con_Tax() {
		return Special_Con_Tax;
	}

	public void setSpecial_Con_Tax(float special_Con_Tax) {
		Special_Con_Tax = special_Con_Tax;
	}

	public float getDiscount_rate() {
		return Discount_rate;
	}

	public void setDiscount_rate(float discount_rate) {
		Discount_rate = discount_rate;
	}

	public short getVAT() {
		return VAT;
	}

	public void setVAT(short VAT) {
		this.VAT = VAT;
	}

	public float getUnit_Price_After_Discount() {
		return Unit_Price_After_Discount;
	}

	public void setUnit_Price_After_Discount(float unit_Price_After_Discount) {
		Unit_Price_After_Discount = unit_Price_After_Discount;
	}

	public float getAllowance() {
		return allowance;
	}

	public void setAllowance(float allowance) {
		this.allowance = allowance;
	}

	public float getUnit_Price_W_Tax_Profit() {
		return Unit_Price_W_Tax_Profit;
	}

	public void setUnit_Price_W_Tax_Profit(float unit_Price_W_Tax_Profit) {
		Unit_Price_W_Tax_Profit = unit_Price_W_Tax_Profit;
	}

	public float getSubcon_Profit() {
		return Subcon_Profit;
	}

	public void setSubcon_Profit(float subcon_Profit) {
		Subcon_Profit = subcon_Profit;
	}

	public float getUnit_Price_W_Tax_Labour() {
		return Unit_Price_W_Tax_Labour;
	}

	public void setUnit_Price_W_Tax_Labour(float unit_Price_W_Tax_Labour) {
		Unit_Price_W_Tax_Labour = unit_Price_W_Tax_Labour;
	}

	public float getCost_Mat_Amount_USD() {
		return Cost_Mat_Amount_USD;
	}

	public void setCost_Mat_Amount_USD(float cost_Mat_Amount_USD) {
		Cost_Mat_Amount_USD = cost_Mat_Amount_USD;
	}

	public float getCost_Labour_Amount_USD() {
		return Cost_Labour_Amount_USD;
	}

	public void setCost_Labour_Amount_USD(float cost_Labour_Amount_USD) {
		Cost_Labour_Amount_USD = cost_Labour_Amount_USD;
	}
}
