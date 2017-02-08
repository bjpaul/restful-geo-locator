package org.locator.geo.rest.cache;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.locator.geo.rest.config.GeoLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;


public class BootstrapLocation implements InitializingBean{
	private static final Logger logger = LoggerFactory.getLogger(BootstrapLocation.class);
	private GeoLocator locator;
	private JdbcTemplate jdbcTemplate;

	public BootstrapLocation(GeoLocator locator, JdbcTemplate jdbcTemplate) {
		this.locator = locator;
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("Bootstrap :: Coordinates");
				bootstrapCoordinates();
				
				logger.info("Bootstrap :: Keywords");
				bootstrapKeywords();
				
			}
		});
		
		thread.start();
		
	}

	private void bootstrapCoordinates() {
		List<String> strs = fetchPopularCitiesCoordinates();
		for (String latlng : strs) {
			String[] strings = latlng.split(",");
			Double lat = Double.parseDouble(strings[0]);
			Double lng = Double.parseDouble(strings[1]);
			locator.findByCoordinates(lat, lng);
		}
	}
	
	private void bootstrapKeywords(){
		Set<String> popularCities = new HashSet<String>(Arrays.asList( "New Delhi","Delhi", "Mumbai","Kolkata","Noida","Pune","Hyderabad","Bengaluru","Chennai","Visakhapatnam","Chandigarh"));
        Set<String> keywords = new TreeSet<String>();

        for(String city: popularCities){
            city = city.toLowerCase();
            for(int i =1; i< city.length() && i<= 3;i++){
                keywords.add(city.substring(0,i));
            }
        }
        for (String keyword : keywords)
        	locator.findByKeyword(keyword);
	}
	
	private List<String> fetchPopularCitiesCoordinates() {
		logger.info("Fetching popular cities coordiantes ");
		
		return jdbcTemplate.queryForList("SELECT distinct(CONCAT(TRUNCATE(latitude,2),',',TRUNCATE(longitude,2))) as coordinates \n" +
                        "FROM `content_city_geo` \n" +
                        "WHERE \n" +
                        "city_name in \n" +
                        "('New Delhi', 'Mumbai','Kolkata','Noida','Pune','Hyderabad','Bengaluru','Chennai','Visakhapatnam','Chandigarh') \n" +
                        "order by coordinates", String.class);
	}


}
