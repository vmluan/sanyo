package com.sanyo.quote.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Chuong on 11/27/2015.
 */
@Entity
@Table(name = "ProductGroupRate", catalog = "sanyo")
public class ProductGroupRate {

    int id;
    float discount;
    float allowance;
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
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "product_group_id", nullable = false)
    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    @JsonIgnore
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID", nullable = false)
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
