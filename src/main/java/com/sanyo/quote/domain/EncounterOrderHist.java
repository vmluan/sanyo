package com.sanyo.quote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * to store which encounter that has changed order.
 * As we can not update other records in the same table (encounter). database does no allow this.
 * Creating this table to store the encounter that has changed order, then call another trigger to update other 
 * records in encounter table.
 */
@Entity
@Table(name = "encounter_order_hist", catalog = "sanyo")
public class EncounterOrderHist {
	private Integer id;
	private Integer encounterId;
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
	@Column(name = "ENCOUNTER_ID", nullable = false)
	public Integer getEncounterId() {
		return encounterId;
	}
	public void setEncounterId(Integer encounterId) {
		this.encounterId = encounterId;
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
