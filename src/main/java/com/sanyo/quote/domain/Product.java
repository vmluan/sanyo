package com.sanyo.quote.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Table;
import javax.persistence.Transient;

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
	
	@Column(name ="IN_PRICE")
	private long inPrice;
	@Column(name ="OUT_WHLSE_PRICE")
	private long outWholeSalePrice;
	@Column(name ="OUT_SALE_PRICE")
	private long outSalePrice;
	@Column(name ="MIN_DISC_WHSLE_PER")
	private short minDiscountWholeSalePer;
	@Column(name ="MAX_DISC_WHSLE_PER")
	private short maxDiscountWholeSalePer;
	
	@Column(name ="MIN_DISC_SALE_PER")
	private short minDiscountSalePer;
	
	@Column(name ="MAX_DISC_SALE_PER")
	private short maxDiscountSalePer;
	
	@Column(name="VAT")
	private short VAT;
	
	@Column(name="LAST_MODIFIED_BY")
	private String lastModifiedBy;
	
	

	public long getInPrice() {
		return inPrice;
	}

	public void setInPrice(long inPrice) {
		this.inPrice = inPrice;
	}

	public long getOutWholeSalePrice() {
		return outWholeSalePrice;
	}

	public void setOutWholeSalePrice(long outWholeSalePrice) {
		this.outWholeSalePrice = outWholeSalePrice;
	}

	public long getOutSalePrice() {
		return outSalePrice;
	}

	public void setOutSalePrice(long outSalePrice) {
		this.outSalePrice = outSalePrice;
	}

	public short getMinDiscountWholeSalePer() {
		return minDiscountWholeSalePer;
	}

	public void setMinDiscountWholeSalePer(short minDiscountWholeSalePer) {
		this.minDiscountWholeSalePer = minDiscountWholeSalePer;
	}

	public short getMaxDiscountWholeSalePer() {
		return maxDiscountWholeSalePer;
	}

	public void setMaxDiscountWholeSalePer(short maxDiscountWholeSalePer) {
		this.maxDiscountWholeSalePer = maxDiscountWholeSalePer;
	}

	public short getMinDiscountSalePer() {
		return minDiscountSalePer;
	}

	public void setMinDiscountSalePer(short minDiscountSalePer) {
		this.minDiscountSalePer = minDiscountSalePer;
	}

	public short getMaxDiscountSalePer() {
		return maxDiscountSalePer;
	}

	public void setMaxDiscountSalePer(short maxDiscountSalePer) {
		this.maxDiscountSalePer = maxDiscountSalePer;
	}

	public short getVAT() {
		return VAT;
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

	@Override
	public String toString(){
		return "'" + productName + "':" + "{"
				+ "pic: " +"'" + picLocation + "'"
				+","
				+ "price: " + String.valueOf(this.outSalePrice)
				+ "}";
	}
}
