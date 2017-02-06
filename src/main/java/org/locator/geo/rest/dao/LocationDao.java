package org.locator.geo.rest.dao;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.locator.geo.rest.config.GeoLocator;
import org.locator.geo.rest.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.record.City;

public class LocationDao implements GeoLocator, InitializingBean{
	
	private static final Logger logger = LoggerFactory.getLogger(LocationDao.class);

	private Resource maxDb;
	private DatabaseReader builder;

	private JdbcTemplate jdbcTemplate;
	
	public LocationDao(Resource maxDb, JdbcTemplate jdbcTemplate) throws IOException {
		super();
		this.maxDb = maxDb;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Location> findByKeyword(String keyword) {
		logger.info("Fetching result by keyword :: "+keyword);
		return jdbcTemplate.query("CALL search_by_keyword(?)",
				new Object[] { keyword }, Location.map());
	}

	@Transactional(readOnly = true)
	@Override
	public List<Location> findByCoordinates(Double latitude, Double longitude) {
		logger.info("Fetching result by lat&lng :: "+latitude+", "+longitude);
		return jdbcTemplate.query("CALL geo_search(?, ?)", new Object[] {
				latitude, longitude }, Location.map());
	}

	@Override
	public City findByIP(String ip) throws UnknownHostException,
			IOException, GeoIp2Exception {
		logger.info("Fetching city by IP :: "+ip);
		return reader().city(InetAddress.getByName(ip)).getCity();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Location> findByGeoLocationId(Integer id) {
		logger.info("Fetching result by city geoname id :: "+id);
		return jdbcTemplate
				.query("SELECT latitude, longitude,locale_code, country_code, country_name, state_code,state_name, city_name, time_zone, content_region_name, content_region_id, content_location_name from content_city_geo where geoname_id=?",
						new Object[] { id }, Location.map());
	}

	private DatabaseReader reader() throws IOException {		
		while (builder == null) {
			synchronized(this){
				if(builder == null){
					logger.info("Building maxmind database reader");
					builder = new DatabaseReader.Builder(maxDb.getInputStream()).build();
				}
			}
		}
		return builder;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		reader();
	}
}
