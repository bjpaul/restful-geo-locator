package org.locator.geo.rest.dao;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.locator.geo.rest.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.record.City;

@Repository
public class LocationDao {

	@Value("classpath:ext/GeoIP2-City.mmdb")
	private Resource maxDb;
	private DatabaseReader builder;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = true)
	public List<Location> findByKeyword(String keyword) {
		return jdbcTemplate.query("CALL search_by_keyword(?)",
				new Object[] { keyword }, Location.map());
	}

	@Transactional(readOnly = true)
	public List<Location> findByCoordinates(Double latitude, Double longitude) {
		return jdbcTemplate.query("CALL geo_search(?, ?)", new Object[] {
				latitude, longitude }, Location.map());
	}

	public City findByIP(String ip) throws UnknownHostException,
			IOException, GeoIp2Exception {
		return reader().city(InetAddress.getByName(ip)).getCity();
	}

	@Transactional(readOnly = true)
	public List<Location> findByGeoLocationId(Integer id) {
		return jdbcTemplate
				.query("SELECT latitude, longitude,locale_code, country_code, country_name, state_code,state_name, city_name, time_zone, content_region_name, content_region_id, content_location_name from content_city_geo where geoname_id=?",
						new Object[] { id }, Location.map());
	}

	@PostConstruct
	public DatabaseReader reader() {

		if (builder == null) {
			try {
				builder = new DatabaseReader.Builder(maxDb.getInputStream())
						.build();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return builder;
	}
}
