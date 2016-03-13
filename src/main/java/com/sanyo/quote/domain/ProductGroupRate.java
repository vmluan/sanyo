package com.sanyo.quote.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Chuong on 11/27/2015.
 * To store total material and total allowance for summary
 */
@Entity
@Table(name = "productgrouprate", catalog = "sanyo")
public class ProductGroupRate {

    Integer id;
    Float discount; //discount_rate for the GroupProduct
    Float allowance; //Allowance for the GroupProduct

    TotalMaterialLabour totalMaterialLabour; //to connect to discount and allowance rate of these productgroup
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

    @OneToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "total_material_labour_id", nullable = false)
    public TotalMaterialLabour getTotalMaterialLabour() {
        return totalMaterialLabour;
    }

    public void setTotalMaterialLabour(TotalMaterialLabour totalMaterialLabour) {
        this.totalMaterialLabour = totalMaterialLabour;
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
