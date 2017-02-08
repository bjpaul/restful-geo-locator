package org.locator.geo.rest.cache;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.locator.geo.rest.config.GeoLocator;
import org.locator.geo.rest.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.record.City;

public class LocationCache implements GeoLocator{
	private static final Logger logger = LoggerFactory.getLogger(LocationCache.class);
	
	private GeoLocator locator;

	public LocationCache(GeoLocator locator) {
		super();
		this.locator = locator;
	}

	@Cacheable(value=CacheNames.KEYWORD, condition="#keyword.length() < 4")
	@Override
	public List<Location> findByKeyword(String keyword) {
		return locator.findByKeyword(keyword);
	}

	@Cacheable(value=CacheNames.LATLNG, keyGenerator="coordinatesKey")
	@Override
	public List<Location> findByCoordinates(Double latitude, Double longitude) {
		return locator.findByCoordinates(latitude, longitude);
	}

	@Cacheable(value=CacheNames.IP)
	@Override
	public City findByIP(String ip) throws UnknownHostException, IOException,
			GeoIp2Exception {
		return locator.findByIP(ip);
	}

	@Cacheable(value=CacheNames.GEOID)
	@Override
	public List<Location> findByGeoLocationId(Integer id) {
		return locator.findByGeoLocationId(id);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		locator.afterPropertiesSet();
		logger.info("LocationCache :: after property set");
	}
}
