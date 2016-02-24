package com.sanyo.quote.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Chuong on 11/27/2015.
 * To store Discount rate and Allowance rate for each ProductGroup belongs to a project
 *
 * By changing these rating from Summary screen of project. The Rating (Discount rate and Allowance)
 * of the product related to ProductGroup will be changed relevantly
 */
@Entity
@Table(name = "productgrouprate", catalog = "sanyo")
public class ProductGroupRate {

    Integer id;
    Float discount; //discount_rate for the GroupProduct
    Float allowance; //Allowance for the GroupProduct
    Float totalMaterial; //This attribute is to support for getting total Material which calculated from encounter trigger easier
    Float totalLabor; //This attribute is to support for getting total Labor which calculated from encounter trigger easier
    ProductGroup productGroup;
    Project project;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_group_rate_id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "discount_rate", nullable = true)
    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    @Column(name = "allowance_rate", nullable = true)
    public Float getAllowance() {
        return allowance;
    }

    public void setAllowance(Float allowance) {
        this.allowance = allowance;
    }

    @Column(name = "total_material", nullable = true)
    public Float getTotalMaterial() {
        return totalMaterial;
    }

    public void setTotalMaterial(Float totalMaterial) {
        this.totalMaterial = totalMaterial;
    }

    @Column(name = "total_labor", nullable = true)
    public Float getTotalLabor() {
        return totalLabor;
    }

    public void setTotalLabor(Float totalLabor) {
        this.totalLabor = totalLabor;
    }

    @OneToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "product_group_id", nullable = false)
    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }


    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "PROJECT_ID", nullable = false)
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
