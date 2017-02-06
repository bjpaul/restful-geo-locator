package org.locator.geo.rest.cache;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.locator.geo.rest.config.GeoLocator;
import org.locator.geo.rest.model.Location;
import org.springframework.cache.annotation.Cacheable;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.record.City;

public class LocationCache implements GeoLocator{
	
	private GeoLocator provider;

	public LocationCache(GeoLocator provider) {
		super();
		this.provider = provider;
	}

	@Cacheable(value=CacheNames.KEYWORD, condition="#keyword.length() < 4")
	@Override
	public List<Location> findByKeyword(String keyword) {
		return provider.findByKeyword(keyword);
	}

	@Cacheable(value=CacheNames.LATLNG, keyGenerator="coordinatesKey")
	@Override
	public List<Location> findByCoordinates(Double latitude, Double longitude) {
		return provider.findByCoordinates(latitude, longitude);
	}

	@Cacheable(value=CacheNames.IP)
	@Override
	public City findByIP(String ip) throws UnknownHostException, IOException,
			GeoIp2Exception {
		return provider.findByIP(ip);
	}

	@Cacheable(value=CacheNames.GEOID)
	@Override
	public List<Location> findByGeoLocationId(Integer id) {
		return provider.findByGeoLocationId(id);
	}

}
