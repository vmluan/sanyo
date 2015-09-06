/**
 * Copyright (c) 2015  osgicse group.
 * Filename   : ChartTypeData.java
 * Description: 
 * @author    : Luan Vo
 * Created    : Oct 17, 2013
 */
package com.sanyo.quote.web.form;

import java.io.Serializable;

public class ChartTypeData  implements Serializable{

	private Integer charttypeid;
	private String chartypecode;
	private String urlimage;
	private String chartypename;	
	
	
	/**
	 * @return the chartypename
	 */
	public String getChartypename() {
		return chartypename;
	}
	/**
	 * @param chartypename the chartypename to set
	 */
	public void setChartypename(String chartypename) {
		this.chartypename = chartypename;
	}
	/**
	 * @return the charttypeid
	 */
	public Integer getCharttypeid() {
		return charttypeid;
	}
	/**
	 * @param charttypeid the charttypeid to set
	 */
	public void setCharttypeid(Integer charttypeid) {
		this.charttypeid = charttypeid;
	}
	/**
	 * @return the chartypecode
	 */
	public String getChartypecode() {
		return chartypecode;
	}
	/**
	 * @param chartypecode the chartypecode to set
	 */
	public void setChartypecode(String chartypecode) {
		this.chartypecode = chartypecode;
	}
	/**
	 * @return the urlimage
	 */
	public String getUrlimage() {
		return urlimage;
	}
	/**
	 * @param urlimage the urlimage to set
	 */
	public void setUrlimage(String urlimage) {
		this.urlimage = urlimage;
	}
}
