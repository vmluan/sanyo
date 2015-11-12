package com.sanyo.quote.domain;

import org.hibernate.annotations.ManyToAny;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Created by User on 10/10/2015.
 */
@Entity
@Table(name = "expenses", catalog = "sanyo")
public class Expenses implements Serializable {
	private Integer expenseID;
	private Project project;
	private ExpenseElements expenseElement;
	private Integer order;
	private  float quantity;
	private float duration; //duration in some records may be null
	private float rate;
	private String remark;
	private float Sum;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="EXPENSE_ID")
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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "EXPENSEELEMENT_ID", nullable = false)
	public ExpenseElements getExpenseElement() {
		return expenseElement;
	}

	public void setExpenseElement(ExpenseElements expenseElement) {
		this.expenseElement = expenseElement;
	}
	
	@Column(name="ORDER_NO")
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
	@Column(name="QUANTITY")
	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	
	@Column(name="DURATION")
	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}
	
	@Column(name="RATE")
	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}
	
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="SUM")
	public float getSum() {
		return Sum;
	}

	public void setSum(float sum) {
		Sum = sum;
	}
}
