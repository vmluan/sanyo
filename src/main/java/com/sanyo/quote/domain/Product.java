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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
@Entity
@Table(name = "product", catalog = "sanyo")
public class Product implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCT_ID", nullable = false)
	private Integer productID;
	
	@Column(name = "PRODUCT_NAME", unique=false)
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
	
	
	@ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
	@JoinTable(name="product_category", joinColumns={@JoinColumn(name="PRODUCT_ID")}
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
	private float vat;
	
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
	private Float mat_w_o_Tax_USD; //Mat w/o Tax USD

	@Column(name="TAX_VND")
	private Float mat_w_o_Tax_VND; //Mat w/o Tax VND

	@Column(name="LABOUR")
	private Float labour; //Nhan cong tung hang muc

	@Column(name="Imp_Tax")
	private float imp_Tax; //Imp Tax

	@Column(name="Special_Con_Tax")
	private float special_Con_Tax; // Special con. Tax

	@Column(name="Discount_rate")
	private float discount_rate; //Discount rate %
	
	private String specification;
	
//	@JsonIgnore
//	@OneToMany(fetch=FetchType.LAZY, mappedBy="product")
//	private Set<Price> prices;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="product_group_id", nullable = false)
	private ProductGroup productGroup;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="maker_id", nullable = true)
	private Maker maker;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date startDate;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date endDate;
	
	@Transient
	private String startDateString;
	@Transient
	private String endDateString;
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="product")
	private Set<LabourPrice> labourPrices;
	
	private String productNameVietnamese;
	
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

	public Float getLabour() {
		return labour;
	}

	public void setLabour(Float labour) {
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
//
//	public Set<Price> getPrices() {
//		return prices;
//	}
//
//	public void setPrices(Set<Price> prices) {
//		this.prices = prices;
//	}

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public float getVat() {
		return vat;
	}

	public void setVat(float vat) {
		this.vat = vat;
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

	public float getImp_Tax() {
		return imp_Tax;
	}

	public void setImp_Tax(float imp_Tax) {
		this.imp_Tax = imp_Tax;
	}

	public float getSpecial_Con_Tax() {
		return special_Con_Tax;
	}

	public void setSpecial_Con_Tax(float special_Con_Tax) {
		this.special_Con_Tax = special_Con_Tax;
	}

	public float getDiscount_rate() {
		return discount_rate;
	}

	public void setDiscount_rate(float discount_rate) {
		this.discount_rate = discount_rate;
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

	public Set<LabourPrice> getLabourPrices() {
		return labourPrices;
	}

	public void setLabourPrices(Set<LabourPrice> labourPrices) {
		this.labourPrices = labourPrices;
	}

	public String getStartDateString() {
		return startDateString;
	}

	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}

	public String getEndDateString() {
		return endDateString;
	}

	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}

	public Maker getMaker() {
		return maker;
	}

	public void setMaker(Maker maker) {
		this.maker = maker;
	}

	public String getProductNameVietnamese() {
		return productNameVietnamese;
	}

	public void setProductNameVietnamese(String productNameVietnamese) {
		this.productNameVietnamese = productNameVietnamese;
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
