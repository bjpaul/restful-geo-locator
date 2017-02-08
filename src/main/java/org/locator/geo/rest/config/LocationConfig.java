package org.locator.geo.rest.config;

import java.io.IOException;

import org.locator.geo.rest.cache.LocationCache;
import org.locator.geo.rest.dao.LocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class LocationConfig {
	
	@Value("classpath:ext/GeoIP2-City.mmdb")
	private Resource maxDb;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Bean
	public GeoLocator provider() throws IOException{
		return new LocationCache(new LocationDao(maxDb, jdbcTemplate));
	}

}
