package com.sanyo.quote.domain;

/**
 * Created by User on 10/11/2015.
 */
public enum ExpensesType {
    NORMAL,//for normal records in the main table of sheet
    SUB_TITLE, //for Sub-title records in the main table of sheet
    EXTRA_TABLE, //for extra calculation
    REFERENCE //for fields that reference from other data in the system
}
