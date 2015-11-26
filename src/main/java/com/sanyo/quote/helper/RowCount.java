package com.sanyo.quote.helper;

public class RowCount{
	int rowCount = 0;

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public void addMoreValue(int value){
		this.rowCount += value;
	}
}
