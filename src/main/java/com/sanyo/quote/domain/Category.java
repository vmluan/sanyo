package com.sanyo.quote.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
/*
 * it is used to store 'Vung' information
 */
@Entity
@Table(name = "sy_category", catalog = "sanyo")
public class Category implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categoryid", nullable = false)
	private Integer categoryID;
	
	@Column(name = "categoryname")
	private String categoryName;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories")
	private List<Product> products;
	
	@Column(name = "parentcategoryid")
	private Category parentCategory;
	
	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
}
