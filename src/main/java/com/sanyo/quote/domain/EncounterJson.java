package com.sanyo.quote.domain;

import java.util.Set;

/*
 * it is used to convert json data to java object.
 */
public class EncounterJson {
	private Integer encounterID;
//	private Product product; //"Description", "productCode", "Unit" will be showed on the UI from
	private int order; //No. STT dung de sap xep cac hang muc
	private String unitRate; //Gia mac dinh lay theo USD
	private String quantity; //So luong, khoi luong
	private String actualQuantity; //So luong, khoi luong thuc te = quantity * hao hut % ben sheet Summary (mac dinh)
	private String amount; // = actualQuantity * unitRate. Don vi tinh theo USD
	private String remark;
	private String mat_w_o_Tax_USD; //Mat w/o Tax USD
	private String mat_w_o_Tax_VND; //Mat w/o Tax VND
	private String labour; //lấy giá trị trong database phần nhân công từng hạng mục.
	private String imp_Tax; //Imp Tax. mặc định ="0%", lấy từ bản thông số chung của dự án
	private String special_Con_Tax; // Special con. Tax. mặc định ="0%", lấy từ bản thông số chung của dự án
	private String discount_rate; //Discount rate %. mặc định ="100%", lấy từ bản thông số chung của dự án
	private String encounterTime;
	private String vat; // mặc định ="0%", lấy từ bản thông số chung của dự án
	private String unit_Price_After_Discount; // = Mat_w_o_Tax_USD + (Mat_w_o_Tax_VND/Ti_gia_VND_to_USD)
	private String allowance; //mặc định 105%, lấy từ bản thông số chung của dự án
	private String unit_Price_W_Tax_Profit; //Unit_Price_After_Discount * (1+(1+Special_Con_Tax*(1+Imp_Tax))*VAT)*Discount_rate
	private String subcon_Profit; //mặc định 105%, lấy từ bản thông số chung của dự án
	private String unit_Price_W_Tax_Labour; //=labour*Subcon_Profit
	private String cost_Mat_Amount_USD; // = Unit_Price_After_Discount * quantity
	private String cost_Labour_Amount_USD; //=Unit_Price_W_Tax_Labour* quantity
	
	private String productId;
	private String productName;
	private String productCode;
	private String regionId;
	private String regionName;
	private String labourAfterTax;
	
//	private Set<LocationJson> locations;
	public Integer getEncounterID() {
		return encounterID;
	}
	public void setEncounterID(Integer encounterID) {
		this.encounterID = encounterID;
	}

	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getUnitRate() {
		return unitRate;
	}
	public void setUnitRate(String unitRate) {
		this.unitRate = unitRate;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getActualQuantity() {
		return actualQuantity;
	}
	public void setActualQuantity(String actualQuantity) {
		this.actualQuantity = actualQuantity;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMat_w_o_Tax_USD() {
		return mat_w_o_Tax_USD;
	}
	public void setMat_w_o_Tax_USD(String mat_w_o_Tax_USD) {
		this.mat_w_o_Tax_USD = mat_w_o_Tax_USD;
	}
	public String getMat_w_o_Tax_VND() {
		return mat_w_o_Tax_VND;
	}
	public void setMat_w_o_Tax_VND(String mat_w_o_Tax_VND) {
		this.mat_w_o_Tax_VND = mat_w_o_Tax_VND;
	}
	public String getLabour() {
		return labour;
	}
	public void setLabour(String labour) {
		this.labour = labour;
	}
	public String getImp_Tax() {
		return imp_Tax;
	}
	public void setImp_Tax(String imp_Tax) {
		this.imp_Tax = imp_Tax;
	}
	public String getSpecial_Con_Tax() {
		return special_Con_Tax;
	}
	public void setSpecial_Con_Tax(String special_Con_Tax) {
		this.special_Con_Tax = special_Con_Tax;
	}
	public String getDiscount_rate() {
		return discount_rate;
	}
	public void setDiscount_rate(String discount_rate) {
		this.discount_rate = discount_rate;
	}
	public String getEncounterTime() {
		return encounterTime;
	}
	public void setEncounterTime(String encounterTime) {
		this.encounterTime = encounterTime;
	}

	public String getUnit_Price_After_Discount() {
		return unit_Price_After_Discount;
	}
	public void setUnit_Price_After_Discount(String unit_Price_After_Discount) {
		this.unit_Price_After_Discount = unit_Price_After_Discount;
	}
	public String getAllowance() {
		return allowance;
	}
	public void setAllowance(String allowance) {
		this.allowance = allowance;
	}
	public String getUnit_Price_W_Tax_Profit() {
		return unit_Price_W_Tax_Profit;
	}
	public void setUnit_Price_W_Tax_Profit(String unit_Price_W_Tax_Profit) {
		this.unit_Price_W_Tax_Profit = unit_Price_W_Tax_Profit;
	}
	public String getSubcon_Profit() {
		return subcon_Profit;
	}
	public void setSubcon_Profit(String subcon_Profit) {
		this.subcon_Profit = subcon_Profit;
	}
	public String getUnit_Price_W_Tax_Labour() {
		return unit_Price_W_Tax_Labour;
	}
	public void setUnit_Price_W_Tax_Labour(String unit_Price_W_Tax_Labour) {
		this.unit_Price_W_Tax_Labour = unit_Price_W_Tax_Labour;
	}
	public String getCost_Mat_Amount_USD() {
		return cost_Mat_Amount_USD;
	}
	public void setCost_Mat_Amount_USD(String cost_Mat_Amount_USD) {
		this.cost_Mat_Amount_USD = cost_Mat_Amount_USD;
	}
	public String getCost_Labour_Amount_USD() {
		return cost_Labour_Amount_USD;
	}
	public void setCost_Labour_Amount_USD(String cost_Labour_Amount_USD) {
		this.cost_Labour_Amount_USD = cost_Labour_Amount_USD;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
//	public Set<LocationJson> getLocations() {
//		return locations;
//	}
//	public void setLocations(Set<LocationJson> locations) {
//		this.locations = locations;
//	}
	public String getVat() {
		return vat;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}
	public String getLabourAfterTax() {
		return labourAfterTax;
	}
	public void setLabourAfterTax(String labourAfterTax) {
		this.labourAfterTax = labourAfterTax;
	}
	
	
}