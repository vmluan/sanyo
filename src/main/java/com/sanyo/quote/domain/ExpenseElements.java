package com.sanyo.quote.domain;

import javax.persistence.*;

/**
 * Created by User on 10/11/2015.
 */


@Entity
@Table(name = "expenseElements", catalog = "sanyo")
public class ExpenseElements implements Serializable {
    //removed the old design
    //NORMAL,//for normal records in the main table of sheet
    //SUB_TITLE, //for Sub-title records in the main table of sheet
    //EXTRA_TABLE, //for extra calculation
    //REFERENCE //for fields that reference from other data in the system

    private Integer expenseElementID;
    private String elementName;
    private Integer defaultOrder;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXPENSEELEMENT_ID", nullable = false)
    public Integer getExpenseElementID() {
        return expenseElementID;
    }

    public void setExpenseElementID(Integer expenseElementID) {
        this.expenseElementID = expenseElementID;
    }

    @Column(name="ELEMENT_NAME")
    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    @Column(name="DEFAULT_ORDER")
    public Integer getDefaultOrder() {
        return defaultOrder;
    }

    public void setDefaultOrder(Integer defaultOrder) {
        this.defaultOrder = defaultOrder;
    }
}
