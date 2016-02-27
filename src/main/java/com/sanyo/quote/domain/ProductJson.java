package com.sanyo.quote.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
 * it is used to convert json data to java object.
 */
public class ProductJson {
	private Integer productID;
	private String productName;
	
	private String picLocation;
	
	private Boolean common = false;
	
	String productPriceWrapper;
	
	private Boolean isDeleted;
	private Date lastUpdated;
	private Date deltedDate;
	private List<CategoryJson> categories;

	private Float minDiscountWholeSalePer;
	private Float maxDiscountWholeSalePer;
	private Float minDiscountSalePer;
	private Float maxDiscountSalePer;
	private Float vat;
	private String lastModifiedBy;
	private String productCode; //Ma san pham
	private String unit; // don vi
	private Float mat_w_o_Tax_USD; //Mat w/o Tax USD
	private Float mat_w_o_Tax_VND; //Mat w/o Tax VND
	private Float labour; //Nhan cong tung hang muc
	private Float imp_Tax; //Imp Tax
	private Float special_Con_Tax; // Special con. Tax
	private Float discount_rate; //Discount rate %
	private String specification;
	private Date startDate;
	private Date endDate;
//	
	private String startDateString;
	private String endDateString;
	private ProductGroupJson productGroup;
	private Integer makerId;
	private String productNameVietnamese;
	
	public Integer getProductID() {
		return productID;
	}
	public void setProductID(Integer productID) {
		this.productID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPicLocation() {
		return picLocation;
	}
	public void setPicLocation(String picLocation) {
		this.picLocation = picLocation;
	}
	public Boolean isCommon() {
		return common;
	}
	public void setCommon(Boolean common) {
		this.common = common;
	}
	public String getProductPriceWrapper() {
		return productPriceWrapper;
	}
	public void setProductPriceWrapper(String productPriceWrapper) {
		this.productPriceWrapper = productPriceWrapper;
	}
	public Boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public Date getDeltedDate() {
		return deltedDate;
	}
	public void setDeltedDate(Date deltedDate) {
		this.deltedDate = deltedDate;
	}
	public List<CategoryJson> getCategories() {
		return categories;
	}
	public void setCategories(List<CategoryJson> categories) {
		this.categories = categories;
	}
	public Float getMinDiscountWholeSalePer() {
		return minDiscountWholeSalePer;
	}
	public void setMinDiscountWholeSalePer(Float minDiscountWholeSalePer) {
		this.minDiscountWholeSalePer = minDiscountWholeSalePer;
	}
	public Float getMaxDiscountWholeSalePer() {
		return maxDiscountWholeSalePer;
	}
	public void setMaxDiscountWholeSalePer(Float maxDiscountWholeSalePer) {
		this.maxDiscountWholeSalePer = maxDiscountWholeSalePer;
	}
	public Float getMinDiscountSalePer() {
		return minDiscountSalePer;
	}
	public void setMinDiscountSalePer(Float minDiscountSalePer) {
		this.minDiscountSalePer = minDiscountSalePer;
	}
	public Float getMaxDiscountSalePer() {
		return maxDiscountSalePer;
	}
	public void setMaxDiscountSalePer(Float maxDiscountSalePer) {
		this.maxDiscountSalePer = maxDiscountSalePer;
	}
	public Float getVat() {
		return vat;
	}
	public void setVat(Float vat) {
		this.vat = vat;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Float getMat_w_o_Tax_USD() {
		return mat_w_o_Tax_USD;
	}
	public void setMat_w_o_Tax_USD(Float mat_w_o_Tax_USD) {
		this.mat_w_o_Tax_USD = mat_w_o_Tax_USD;
	}
	public Float getMat_w_o_Tax_VND() {
		return mat_w_o_Tax_VND;
	}
	public void setMat_w_o_Tax_VND(Float mat_w_o_Tax_VND) {
		this.mat_w_o_Tax_VND = mat_w_o_Tax_VND;
	}
	public Float getLabour() {
		return labour;
	}
	public void setLabour(Float labour) {
		this.labour = labour;
	}
	public Float getImp_Tax() {
		return imp_Tax;
	}
	public void setImp_Tax(Float imp_Tax) {
		this.imp_Tax = imp_Tax;
	}
	public Float getSpecial_Con_Tax() {
		return special_Con_Tax;
	}
	public void setSpecial_Con_Tax(Float special_Con_Tax) {
		this.special_Con_Tax = special_Con_Tax;
	}
	public Float getDiscount_rate() {
		return discount_rate;
	}
	public void setDiscount_rate(Float discount_rate) {
		this.discount_rate = discount_rate;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public ProductGroupJson getProductGroup() {
		return productGroup;
	}
	public void setProductGroup(ProductGroupJson productGroup) {
		this.productGroup = productGroup;
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
	public String getStartDateString() {
		return startDateString;
	}
	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
		if(startDateString != null && !startDateString.trim().equals("")){
			try {
				this.startDate = new SimpleDateFormat("MM/dd/yyyy").parse(startDateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	public String getEndDateString() {
		return endDateString;
	}
	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
		if(endDateString != null && !endDateString.trim().equals(""))
		{
			try {
				this.endDate = new SimpleDateFormat("MM/dd/yyyy").parse(endDateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	public Integer getMakerId() {
		return makerId;
	}
	public void setMakerId(Integer makerId) {
		this.makerId = makerId;
	}
	public String getProductNameVietnamese() {
		return productNameVietnamese;
	}
	public void setProductNameVietnamese(String productNameVietnamese) {
		this.productNameVietnamese = productNameVietnamese;
	}
	
	
}
