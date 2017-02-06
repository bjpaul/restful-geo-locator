package org.locator.geo.rest.cache;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.locator.geo.rest.config.LocationProvider;
import org.locator.geo.rest.model.Location;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.record.City;

public class LocationCache implements LocationProvider{
	
	private LocationProvider provider;

	public LocationCache(LocationProvider provider) {
		super();
		this.provider = provider;
	}

	@Override
	public List<Location> findByKeyword(String keyword) {
		return provider.findByKeyword(keyword);
	}

	@Override
	public List<Location> findByCoordinates(Double latitude, Double longitude) {
		return provider.findByCoordinates(latitude, longitude);
	}

	@Override
	public City findByIP(String ip) throws UnknownHostException, IOException,
			GeoIp2Exception {
		return provider.findByIP(ip);
	}

	@Override
	public List<Location> findByGeoLocationId(Integer id) {
		return provider.findByGeoLocationId(id);
	}

}
