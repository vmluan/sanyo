package com.sanyo.quote.domain;

/**
 * Created by User on 10/28/2015.
 *
 * This class is used to provide UI render and make UI easier to maintain only
 * The attribute has the same formart: expenseElement_id
 * Eg: expenseElement_1, expenseElement_11
 * id is get from table ExpenseElements, we can find the comment about name of these expenseElement_id in this table as well
 */
public class Condition2Json {
	private String checkbox;
	private String contents;
    public String getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
}
