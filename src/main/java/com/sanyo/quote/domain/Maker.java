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

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "maker", catalog = "sanyo")
public class Maker {
	
	private Integer id;
	private String name;
	private String desc;
	private Set<ProductGroupMaker> productGroupMakers;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, mappedBy="maker")
	public Set<ProductGroupMaker> getProductGroupMakers() {
		return productGroupMakers;
	}
	public void setProductGroupMakers(Set<ProductGroupMaker> productGroupMakers) {
		this.productGroupMakers = productGroupMakers;
	}
	
}
