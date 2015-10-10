package com.sanyo.quote.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by User on 10/10/2015.
 */
@Entity
@Table(name = "expenses", catalog = "sanyo")
public class Expenses implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="EXPENSE_ID")
    private Integer expenseID;

    @Column(name="PROJECT_ID")
    private Project project;

    @Column(name="CODE")
    private String code;

    @Column(name="NAME")
    private String name;
    @Column(name="QUANTITY")
    private  float quantity;

    @Column(name="DURATION")
    private float duration;
    @Column(name="RATE")
    private float Rate;
    @Column(name="AMOUNT")
    private float amount;

    @Column(name="remark")
    private String remark; // will available for TYPE is NORMAL as STRING only

    @Column(name="Total_Session_Remark")
    private float Total_Session_Remark; //for total of session only. TYPE is SUB_TITLE


    @Column(name="Extra_Rate")
    private float Extra_Rate;//for extra_table calculation only
    @Column(name="Extra_unit")
    private String Extra_unit;//for extra_table calculation only
    @Column(name="Extra_amount")
    private float Extra_amount;//for extra_table calculation only

    @Column(name="single_float_field")
    private float single_float_field;//for single number field only
    @Column(name="single_String_field")
    private String sing_String_field;//for single String field only


    public Integer getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(Integer expenseID) {
        this.expenseID = expenseID;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROJECT_ID", nullable = false)
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
