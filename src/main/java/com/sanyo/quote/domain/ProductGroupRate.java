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
@Table(name = "ProductGroupRate", catalog = "sanyo")
public class ProductGroupRate {

    int id;
    float discount; //discount_rate for the GroupProduct
    float allowance; //Allowance for the GroupProduct
    ProductGroup productGroup;
    Project project;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_group_rate_id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "discount_rate", nullable = true)
    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    @Column(name = "allowance_rate", nullable = true)
    public float getAllowance() {
        return allowance;
    }

    public void setAllowance(float allowance) {
        this.allowance = allowance;
    }

    @JsonIgnore
    @OneToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "product_group_id", nullable = false)
    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    @JsonIgnore
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "PROJECT_ID", nullable = false)
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
