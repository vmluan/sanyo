package com.sanyo.quote.web.controller;

import java.util.Date;

import com.ibm.icu.util.Calendar;

public class CountDate {
	public int CountMonth(Date startDate, Date endate)
	{
		
		int summonth=0;
		int day = getDate(startDate,endate);
		summonth = day/30;
		return summonth;
	}
	public int CountDay(Date startDate, Date endate)
	{
		int sumDay=0;
		int day = getDate(startDate,endate);
		sumDay = day%7;
		return sumDay;
	}
	public int CountWeek(Date startDate, Date endate)
	{
		int sumWeek=0;
		int day = getDate(startDate,endate);
		sumWeek = day/7;
		return sumWeek;
	}
	private int getDate(Date startDate, Date endDate)
	{
		int day =0;
		
		Calendar calS = Calendar.getInstance();
	    calS.setTime(startDate);

	    int startmonth = calS.get(Calendar.MONTH);
	    int startDay = calS.get(Calendar.DAY_OF_MONTH);
	    int startyear = calS.get(Calendar.YEAR);
	    
	    Calendar calE = Calendar.getInstance();
	    calE.setTime(endDate);

	    int endmonth = calE.get(Calendar.MONTH);
	    int endDay = calE.get(Calendar.DAY_OF_MONTH);
	    int endyear = calE.get(Calendar.YEAR);
	    
		//int startmonth = startDate.getMonth();
		//int startyear = startDate.getYear();
		//int endmonth = endDate.getMonth();
		//int endyear = endDate.getYear();
		//int startDay = startDate.getDay();
		//int endDay = endDate.getDay();
		day = (endyear - startyear)*365;
		if(startDay==31)
			startDay = 30;
		if((endmonth - startmonth)<=1)
		{
			//if(endDay>)				
				day += endDay + (30 - startDay);
		}
		else
		{
			day += (endmonth - startmonth)*30;
			day += endDay + (30 - startDay);
		}
		return day;
	}
}
