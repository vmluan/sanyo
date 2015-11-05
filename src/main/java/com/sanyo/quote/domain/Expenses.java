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
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="EXPENSE_ID")
	private Integer expenseID;

	private Project project;

	private ExpenseElements expenseElement;

	@Column(name="ORDER_NO")
	private Integer order;

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

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
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
