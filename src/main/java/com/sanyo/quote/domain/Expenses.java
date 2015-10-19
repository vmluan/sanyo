package com.sanyo.quote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "EXPENSE_ELEMENT_ID", nullable = false)
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ExpenseElements getExpenseElement() {
		return expenseElement;
	}

	public void setExpenseElement(ExpenseElements expenseElement) {
		this.expenseElement = expenseElement;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    

}
