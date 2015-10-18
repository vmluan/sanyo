package com.sanyo.quote.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

    @Column(name="EXPENSE_ELEMENT")
    private ExpenseElements expenseElement;

    @Column(name="ORDER")
    private String order;

    @Column(name="QUANTITY")
    private  float quantity;

    @Column(name="DURATION")
    private float duration; //duration in some records may be null

    @Column(name="RATE")
    private float rate;

    @Column(name="remark")
    private String remark;


    public Integer getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(Integer expenseID) {
        this.expenseID = expenseID;
    }

}
