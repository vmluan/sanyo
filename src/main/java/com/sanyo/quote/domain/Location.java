package com.sanyo.quote.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "location", catalog = "sanyo")
public class Location {
	private Integer locationId;
	private String locationName;
	private String locationDesc;
	private Project project;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "LOCATION_ID", unique = true, nullable = false)
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	
	@NotEmpty
	@Column(name = "LOCATION_NAME", nullable = false, length = 100)
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	@Column(name = "LOCATION_DESC", nullable = false, length = 100)
	public String getLocationDesc() {
		return locationDesc;
	}
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

	
}
