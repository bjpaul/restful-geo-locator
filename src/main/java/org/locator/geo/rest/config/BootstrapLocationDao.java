package org.locator.geo.rest.config;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class BootstrapLocationDao {
	private static final Logger logger = LoggerFactory.getLogger(BootstrapLocationDao.class);
	private JdbcTemplate jdbcTemplate;
	
	public BootstrapLocationDao(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	

	@Transactional(readOnly = true)
	public List<String> fetchPopularCitiesCoordinates() {
		logger.info("Fetching popular cities coordiantes ");
		
		return jdbcTemplate.queryForList("SELECT distinct(CONCAT(TRUNCATE(latitude,2),',',TRUNCATE(longitude,2))) as coordinates \n" +
                        "FROM `content_city_geo` \n" +
                        "WHERE \n" +
                        "city_name in \n" +
                        "('New Delhi', 'Mumbai','Kolkata','Noida','Pune','Hyderabad','Bengaluru','Chennai','Visakhapatnam','Chandigarh') \n" +
                        "order by coordinates", String.class);
	}
}
