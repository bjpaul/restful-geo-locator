package org.locator.geo.rest.dao;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.locator.geo.rest.config.LocationProvider;
import org.locator.geo.rest.model.Location;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.record.City;

public class LocationDao implements LocationProvider{

	private Resource maxDb;
	private DatabaseReader builder;

	private JdbcTemplate jdbcTemplate;
	
	public LocationDao(Resource maxDb, JdbcTemplate jdbcTemplate) throws IOException {
		super();
		this.maxDb = maxDb;
		this.jdbcTemplate = jdbcTemplate;
		reader();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Location> findByKeyword(String keyword) {
		return jdbcTemplate.query("CALL search_by_keyword(?)",
				new Object[] { keyword }, Location.map());
	}

	@Transactional(readOnly = true)
	@Override
	public List<Location> findByCoordinates(Double latitude, Double longitude) {
		return jdbcTemplate.query("CALL geo_search(?, ?)", new Object[] {
				latitude, longitude }, Location.map());
	}

	@Override
	public City findByIP(String ip) throws UnknownHostException,
			IOException, GeoIp2Exception {
		return reader().city(InetAddress.getByName(ip)).getCity();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Location> findByGeoLocationId(Integer id) {
		return jdbcTemplate
				.query("SELECT latitude, longitude,locale_code, country_code, country_name, state_code,state_name, city_name, time_zone, content_region_name, content_region_id, content_location_name from content_city_geo where geoname_id=?",
						new Object[] { id }, Location.map());
	}

	private DatabaseReader reader() throws IOException {

		if (builder == null) {
			builder = new DatabaseReader.Builder(maxDb.getInputStream()).build();
		}
		return builder;
	}
}
