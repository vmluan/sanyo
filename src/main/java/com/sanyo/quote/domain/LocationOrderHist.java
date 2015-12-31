package com.sanyo.quote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * to store which location that has changed order.
 * As we can not update other records in the same table (location). database does no allow this.
 * Creating this table to store the encounter that has changed order, then call another trigger to update other 
 * records in encounter table.
 */
@Entity
@Table(name = "location_order_hist", catalog = "sanyo")
public class LocationOrderHist {
	private Integer id;
	private Integer locationId;
	private Integer fromPos;
	private Integer toPos;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "LOCATION_ID", nullable = false)
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	
	public Integer getFromPos() {
		return fromPos;
	}

	public void setFromPos(Integer fromPos) {
		this.fromPos = fromPos;
	}
	public Integer getToPos() {
		return toPos;
	}
	public void setToPos(Integer toPos) {
		this.toPos = toPos;
	}
	

}
