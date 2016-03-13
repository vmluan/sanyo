package com.sanyo.quote.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by chuong on 3/13/16.
 * To store Discount rate and Allowance rate for each ProductGroup belongs to a project
 *
 * By changing these rating from Summary screen of project. The Rating (Discount rate and Allowance)
 * of the product related to ProductGroup will be changed relevantly
 */
@Entity
@Table(name = "totalmateriallabour", catalog = "sanyo")
public class TotalMaterialLabour {

    Integer id;

    Float totalMaterial; //This attribute is to support for getting total Material which calculated from encounter trigger easier
    Float totalLabor; //This attribute is to support for getting total Labor which calculated from encounter trigger easier

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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




}
