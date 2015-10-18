package com.sanyo.quote.domain;

import java.io.Serializable;
import java.util.List;

/*
 * it is used to convert json data to java object.
 */
public class RegionJson implements Serializable{
	private Integer regionId;
	private String regionName;
	private String locationName;
	
	private List<UserJson> users;

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public List<UserJson> getUsers() {
		return users;
	}

	public void setUsers(List<UserJson> users) {
		this.users = users;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	

}
