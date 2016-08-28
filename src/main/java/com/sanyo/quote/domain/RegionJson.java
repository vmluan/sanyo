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
	private Integer locationId;

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	private List<UserJson> users;
	private List<LocationJson> locations;

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

	public List<LocationJson> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationJson> locations) {
		this.locations = locations;
	}

}
