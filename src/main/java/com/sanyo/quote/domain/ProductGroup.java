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
	
}
