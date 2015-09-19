package com.sanyo.quote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "category", catalog = "sanyo")
public class Category implements java.io.Serializable {

	private Integer categoryId;
	private String name;
	private String desc;
	private Set<ProjectCategory> projectCategories = new HashSet<ProjectCategory>(0);


	private List<Product> products;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CATEGORY_ID", unique = true, nullable = false)
	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "NAME", nullable = false, length = 10)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "[DESC]", nullable = false)
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.category")
	public Set<ProjectCategory> getProjectCategories() {
		return this.projectCategories;
	}

	public void setProjectCategories(Set<ProjectCategory> projectCategories) {
		this.projectCategories = projectCategories;
	}
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories")
	public List<Product> getProducts() {
		return products;
	}
	
	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
