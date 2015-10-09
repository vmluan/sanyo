package com.sanyo.quote.domain;

import javax.persistence.*;

/**
 * Created by User on 10/10/2015.
 */
@Entity
@Table(name = "expenses", catalog = "sanyo")
public class Expenses {
    @Column(name="EXPENSE_ID")
    private Integer expenseID;

    @Column(name="PROJECT_ID")
    private Project project;

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
