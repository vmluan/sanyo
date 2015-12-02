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
public class Encounter implements Serializable, Cloneable{
	private Integer encounterID;
	private Product product; //"Description", "productCode", "Unit" will be showed on the UI from
	private int order; //No. STT dung de sap xep cac hang muc
	private float unitRate; //Gia mac dinh lay theo USD
	private float quantity; //So luong, khoi luong
	private float actualQuantity; //So luong, khoi luong thuc te = quantity * hao hut % ben sheet Summary (mac dinh)
	private float amount; // = actualQuantity * unitRate. Don vi tinh theo USD
	private String remark;
	private float Mat_w_o_Tax_USD; //Mat w/o Tax USD
	private float Mat_w_o_Tax_VND; //Mat w/o Tax VND
	private float labour; //lấy giá trị trong database phần nhân công từng hạng mục.
	//Theo thiết kế thì lấy từ bảng price với PriceType là LABOUR
	private float Imp_Tax; //Imp Tax. mặc định ="0%", lấy từ bản thông số chung của dự án
	private float Special_Con_Tax; // Special con. Tax. mặc định ="0%", lấy từ bản thông số chung của dự án

	/*
	Discount_rate here is designed for rating per PRODUCT
	While this rate will actually be gotten by discount_rate of productgroup
	refer to ProductGroupRate class for more detail of rating
	 */
	private float Discount_rate; //Discount rate %. mặc định ="100%", lấy từ bản thông số chung của dự án

	private Date encounterTime;
	private float VAT; // mặc định ="0%", lấy từ bản thông số chung của dự án
	private float Unit_Price_After_Discount; // = Mat_w_o_Tax_USD + (Mat_w_o_Tax_VND/Ti_gia_VND_to_USD)

	/*
	Allowance here is designed for rating per PRODUCT
	While this rate will actually be gotten by discount_rate of productgroup
	refer to ProductGroupRate class for more detail of rating
	 */
	private float allowance; //mặc định 105%, lấy từ bản thông số chung của dự án

	private float Unit_Price_W_Tax_Profit; //Unit_Price_After_Discount * (1+(1+Special_Con_Tax*(1+Imp_Tax))*VAT)*Discount_rate
	private float Subcon_Profit; //mặc định 105%, lấy từ bản thông số chung của dự án
	private float Unit_Price_W_Tax_Labour; //=labour*Subcon_Profit
	private float Cost_Mat_Amount_USD; // = Unit_Price_After_Discount * quantity
	private float Cost_Labour_Amount_USD; //=Unit_Price_W_Tax_Labour* quantity
	private Region region;
	private EncounterStatus status;
//	private Location location;
	private float labourAfterTax;
	private Float nonamePercent;
	private String nonameRange;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ENCOUNTER_ID", nullable = false)
	public Integer getEncounterID() {
		return encounterID;
	}
	public void setEncounterID(Integer encounterID) {
		this.encounterID = encounterID;
	}
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(name="ENCOUNTER_TIME")
	public Date getEncounterTime() {
		return encounterTime;
	}

	public void setEncounterTime(Date encounterTime) {
		this.encounterTime = encounterTime;
	}
	
	@ManyToOne(cascade = CascadeType.MERGE)
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
	@Column(name="ORDER_NO")
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	@Column(name="UNIT_RATE")
	public float getUnitRate() {
		return unitRate;
	}

	public void setUnitRate(float unitRate) {
		this.unitRate = unitRate;
	}

	@Column(name="QUANTITY")
	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	
	@Column(name="ACTUAL_QUANTITY")
	public float getActualQuantity() {
		return actualQuantity;
	}

	public void setActualQuantity(float actualQuantity) {
		this.actualQuantity = actualQuantity;
	}
	@Column(name="AMOUNT")
	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	@Column(name="REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="TAX_USD")
	public float getMat_w_o_Tax_USD() {
		return Mat_w_o_Tax_USD;
	}

	public void setMat_w_o_Tax_USD(float mat_w_o_Tax_USD) {
		Mat_w_o_Tax_USD = mat_w_o_Tax_USD;
	}

	@Column(name="TAX_VND")
	public float getMat_w_o_Tax_VND() {
		return Mat_w_o_Tax_VND;
	}

	public void setMat_w_o_Tax_VND(float mat_w_o_Tax_VND) {
		Mat_w_o_Tax_VND = mat_w_o_Tax_VND;
	}

	@Column(name="LABOUR")
	public float getLabour() {
		return labour;
	}

	public void setLabour(float labour) {
		this.labour = labour;
	}

	@Column(name="Imp_Tax")
	public float getImp_Tax() {
		return Imp_Tax;
	}

	public void setImp_Tax(float imp_Tax) {
		Imp_Tax = imp_Tax;
	}

	@Column(name="Special_Con_Tax")
	public float getSpecial_Con_Tax() {
		return Special_Con_Tax;
	}

	public void setSpecial_Con_Tax(float special_Con_Tax) {
		Special_Con_Tax = special_Con_Tax;
	}
	
	@Column(name="Discount_rate")
	public float getDiscount_rate() {
		return Discount_rate;
	}

	public void setDiscount_rate(float discount_rate) {
		Discount_rate = discount_rate;
	}

	@Column(name="VAT")
	public float getVAT() {
		return VAT;
	}

	public void setVAT(float VAT) {
		this.VAT = VAT;
	}

	@Column(name="Unit_Price_After_Discount")
	public float getUnit_Price_After_Discount() {
		return Unit_Price_After_Discount;
	}

	public void setUnit_Price_After_Discount(float unit_Price_After_Discount) {
		Unit_Price_After_Discount = unit_Price_After_Discount;
	}
	
	@Column(name="ALLOWANCE")
	public float getAllowance() {
		return allowance;
	}

	public void setAllowance(float allowance) {
		this.allowance = allowance;
	}
	
	@Column(name="Unit_Price_W_Tax_Profit")
	public float getUnit_Price_W_Tax_Profit() {
		return Unit_Price_W_Tax_Profit;
	}

	public void setUnit_Price_W_Tax_Profit(float unit_Price_W_Tax_Profit) {
		Unit_Price_W_Tax_Profit = unit_Price_W_Tax_Profit;
	}

	@Column(name="Subcon_Profit")
	public float getSubcon_Profit() {
		return Subcon_Profit;
	}

	public void setSubcon_Profit(float subcon_Profit) {
		Subcon_Profit = subcon_Profit;
	}
	
	@Column(name="Unit_Price_W_Tax_Labour")
	public float getUnit_Price_W_Tax_Labour() {
		return Unit_Price_W_Tax_Labour;
	}

	public void setUnit_Price_W_Tax_Labour(float unit_Price_W_Tax_Labour) {
		Unit_Price_W_Tax_Labour = unit_Price_W_Tax_Labour;
	}

	@Column(name="Cost_Mat_Amount_USD")
	public float getCost_Mat_Amount_USD() {
		return Cost_Mat_Amount_USD;
	}

	public void setCost_Mat_Amount_USD(float cost_Mat_Amount_USD) {
		Cost_Mat_Amount_USD = cost_Mat_Amount_USD;
	}

	@Column(name="Cost_Labour_Amount_USD")
	public float getCost_Labour_Amount_USD() {
		return Cost_Labour_Amount_USD;
	}

	public void setCost_Labour_Amount_USD(float cost_Labour_Amount_USD) {
		Cost_Labour_Amount_USD = cost_Labour_Amount_USD;
	}
	public float getLabourAfterTax() {
		return labourAfterTax;
	}
	public void setLabourAfterTax(float labourAfterTax) {
		this.labourAfterTax = labourAfterTax;
	}
	public Float getNonamePercent() {
		return nonamePercent;
	}
	public void setNonamePercent(Float nonamePercent) {
		this.nonamePercent = nonamePercent;
	}
	public String getNonameRange() {
		return nonameRange;
	}
	public void setNonameRange(String nonameRange) {
		this.nonameRange = nonameRange;
	}
	
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "LOCATION_ID", nullable = true)
//	public Location getLocation() {
//		return location;
//	}
//	public void setLocation(Location location) {
//		this.location = location;
//	}
	@Override
	public Encounter clone() throws CloneNotSupportedException {
		Encounter clonedEncounter = (Encounter) super.clone();
//		clonedEncounter.setEncounterID(null);
//		clonedEncounter.setRegion(null);
		return clonedEncounter;
	}
	
}
