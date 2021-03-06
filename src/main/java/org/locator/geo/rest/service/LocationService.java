package org.locator.geo.rest.service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.locator.geo.rest.config.GeoLocator;
import org.locator.geo.rest.http.request.dto.LocationList;
import org.locator.geo.rest.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.record.City;

@Service
public class LocationService {

	@Autowired
	private GeoLocator locator;

	public LocationList geoLocation(Double lat, Double lng, String keyword, String ip) throws UnknownHostException,
			IOException, GeoIp2Exception {
		
		List<Location> locations = null;
		
		/*
		 * 
		 * if no keyword and co-ordinates found
		 * 
		 * maxmind implementation
		 */
		if ((lat == null || lat == 0) && (keyword == null || keyword.trim().equals(""))) {
			locations = geoLocationByIp(ip);
		}
		
		/*
		 * 
		 * if keyword found
		 */
		else if (keyword != null && !keyword.trim().equals("")) {
			locations = locator.findByKeyword(keyword);
		}
		
		/*
		 * 
		 * if co-ordinates found
		 */
		else {
			locations = locator.findByCoordinates(lat, lng);
		}

		return LocationList.show(locations, ip);
	}
	
	private List<Location> geoLocationByIp(String ip) throws UnknownHostException, IOException, GeoIp2Exception{
		City city = locator.findByIP(ip);
		return locator.findByGeoLocationId(city.getGeoNameId());
	}
}
