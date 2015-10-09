package com.sanyo.quote.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;
@Entity
@Table(name = "product", catalog = "sanyo")
public class Product implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCT_ID", nullable = false)
	private Integer productID;
	
	@Column(name = "PRODUCT_NAME", unique=true)
	private String productName;
	
//	@Column(name = "PRICE")
//	private long productPrice;
	
	@Column(name = "PIC_LOCATION")
	private String picLocation;
	
	@Column(name = "iscommon")
	private boolean common = false;
	
	@Transient
	MultipartFile file;
	
	@Transient
	String productPriceWrapper;

	@Transient
	String [] categoriesList;
	
	@Column(name="isdeleted")
	private boolean isDeleted;
	
	@Column(name="LAST_UPDATED")
	private Date lastUpdated;
	
	@Column(name ="DELTED_DATE")
	private Date deltedDate;
	
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name="product_category", joinColumns={@JoinColumn(name="productid")}
			, inverseJoinColumns={@JoinColumn(name="CATEGORY_ID")})
	private List<Category> categories;

	@Column(name ="MIN_DISC_WHSLE_PER")
	private float minDiscountWholeSalePer;
	@Column(name ="MAX_DISC_WHSLE_PER")
	private float maxDiscountWholeSalePer;
	
	@Column(name ="MIN_DISC_SALE_PER")
	private float minDiscountSalePer;
	
	@Column(name ="MAX_DISC_SALE_PER")
	private float maxDiscountSalePer;
	
	@Column(name="VAT")
	private float VAT;
	
	@Column(name="LAST_MODIFIED_BY")
	private String lastModifiedBy;

	/*
		Chuong added more attributes Oct 8th 2015
	 */
	@Column(name="PRODUCT_CODE")
	private String productCode; //Ma san pham

	@Column(name="UNIT")
	private String unit; // don vi

	@Column(name="TAX_USD")
	private float Mat_w_o_Tax_USD; //Mat w/o Tax USD

	@Column(name="TAX_VND")
	private float Mat_w_o_Tax_VND; //Mat w/o Tax VND

	@Column(name="LABOUR")
	private float labour; //Nhan cong tung hang muc

	@Column(name="Imp_Tax")
	private float Imp_Tax; //Imp Tax

	@Column(name="Special_Con_Tax")
	private float Special_Con_Tax; // Special con. Tax

	@Column(name="Discount_rate")
	private float Discount_rate; //Discount rate %
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="product")
	private Set<Price> prices;



	public float getDiscount_rate() {
		return Discount_rate;
	}

	public void setDiscount_rate(float discount_rate) {
		Discount_rate = discount_rate;
	}

	public void setMinDiscountWholeSalePer(float minDiscountWholeSalePer) {
		this.minDiscountWholeSalePer = minDiscountWholeSalePer;
	}

	public void setMaxDiscountWholeSalePer(float maxDiscountWholeSalePer) {
		this.maxDiscountWholeSalePer = maxDiscountWholeSalePer;
	}

	public void setMinDiscountSalePer(float minDiscountSalePer) {
		this.minDiscountSalePer = minDiscountSalePer;
	}

	public void setMaxDiscountSalePer(float maxDiscountSalePer) {
		this.maxDiscountSalePer = maxDiscountSalePer;
	}

	public void setVAT(float VAT) {
		this.VAT = VAT;
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

	public void setVAT(short vAT) {
		VAT = vAT;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public String[] getCategoriesList() {
		return categoriesList;
	}

	public void setCategoriesList(String[] categoriesList) {
		this.categoriesList = categoriesList;
	}
	
	
	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
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

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

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

//	public long getProductPrice() {
//		return productPrice;
//	}
//
//	public void setProductPrice(long productPrice) {
//		this.productPrice = productPrice;
//	}

	public String getPicLocation() {
		return picLocation;
	}

	public void setPicLocation(String picLocation) {
		this.picLocation = picLocation;
	}


	public boolean isCommon() {
		return common;
	}

	public void setCommon(boolean common) {
		this.common = common;
	}

	
	public String getProductPriceWrapper() {
		return productPriceWrapper;
	}

	public void setProductPriceWrapper(String productPriceWrapper) {
		this.productPriceWrapper = productPriceWrapper;
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
	

	public float getMinDiscountWholeSalePer() {
		return minDiscountWholeSalePer;
	}

	public float getMaxDiscountWholeSalePer() {
		return maxDiscountWholeSalePer;
	}

	public float getMinDiscountSalePer() {
		return minDiscountSalePer;
	}

	public float getMaxDiscountSalePer() {
		return maxDiscountSalePer;
	}

	public float getVAT() {
		return VAT;
	}
	

	public Set<Price> getPrices() {
		return prices;
	}

	public void setPrices(Set<Price> prices) {
		this.prices = prices;
	}

	@Override
	public String toString(){
		return "'" + productName + "':" + "{"
				+ "pic: " +"'" + picLocation + "'"
				+","
				+ "price: toString() function in Product has not been implemented yet "
				+ "}";
	}
}
