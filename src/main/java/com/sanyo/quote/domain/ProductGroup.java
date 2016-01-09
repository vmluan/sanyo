package com.sanyo.quote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

/*
 * it represent to 'nhom vat tu';
 */
@Entity
@Table(name = "ProductGroup", catalog = "sanyo")
public class ProductGroup {
	private Integer groupId;
	private String groupName;
	private String groupCode;
	private Set<ProductGroupMaker> productGroupMakers;
	private Set<Product> products;
	
	//to get values from productGroupRate and display in the BOQ tabs.
	float discount; //discount_rate for the GroupProduct
    float allowance; //Allowance for the GroupProduct
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "product_group_id", unique = true, nullable = false)
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@NotEmpty
	@NotNull
	@Column(name = "product_group_code", unique = true, nullable = false)
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="productGroup")
	public Set<ProductGroupMaker> getProductGroupMakers() {
		return productGroupMakers;
	}
	public void setProductGroupMakers(Set<ProductGroupMaker> productGroupMakers) {
		this.productGroupMakers = productGroupMakers;
	}
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="productGroup")
	public Set<Product> getProducts() {
		return products;
	}
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	@Transient
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	@Transient
	public float getAllowance() {
		return allowance;
	}
	public void setAllowance(float allowance) {
		this.allowance = allowance;
	}
	
}
