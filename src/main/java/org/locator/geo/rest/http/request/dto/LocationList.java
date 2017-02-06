package org.locator.geo.rest.http.request.dto;

import java.util.List;

import org.locator.geo.rest.model.Location;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationList {
	
	@JsonProperty("client_ip")
	private String clientIp;
	
	@JsonProperty("location_list")
	private List<Location> locationList;

	public List<Location> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}
	
	
	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public static LocationList show(List<Location> locations, String ip){
		LocationList locationList = new LocationList();
		locationList.setLocationList(locations);
		locationList.setClientIp(ip);
		return locationList;
	}

}
