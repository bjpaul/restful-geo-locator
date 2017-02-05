package org.locator.geo.rest.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {

	private Double latitude;
	private Double longitude;
	@JsonProperty("locale_code")
	private String localeCode;
	@JsonProperty("city_name")
	private String cityName;
	@JsonProperty("state_code")
	private String stateCode;
	@JsonProperty("state_name")
	private String stateName;
	@JsonProperty("country_code")
	private String countryCode;
	@JsonProperty("country_name")
	private String countryName;
	@JsonProperty("time_zome")
	private String timeZone;

	public Location() {
		super();
	}

	public Location(String localeCode, String cityName, String stateCode,
			String stateName, String countryCode, String countryName,
			String timeZone, Double latitude, Double longitude) {
		super();
		this.localeCode = localeCode;
		this.cityName = cityName;
		this.stateCode = stateCode;
		this.stateName = stateName;
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.timeZone = timeZone;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public static RowMapper<Location> map() {
		return new RowMapper<Location>() {

			@Override
			public Location mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return new Location(rs.getString("locale_code"),
						rs.getString("city_name"), rs.getString("state_code"),
						rs.getString("state_name"),
						rs.getString("country_code"),
						rs.getString("country_name"),
						rs.getString("time_zone"), rs.getDouble("latitude"),
						rs.getDouble("longitude"));
			}
		};
	}

	@Override
	public String toString() {
		return "Location [latitude=" + latitude + ", longitude=" + longitude
				+ ", localeCode=" + localeCode + ", cityName=" + cityName
				+ ", stateCode=" + stateCode + ", stateName=" + stateName
				+ ", countryCode=" + countryCode + ", countryName="
				+ countryName + ", timeZone=" + timeZone + "]";
	}
	
	

}
