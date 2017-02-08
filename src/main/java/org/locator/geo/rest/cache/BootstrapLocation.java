package org.locator.geo.rest.cache;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.locator.geo.rest.config.BootstrapLocationDao;
import org.locator.geo.rest.config.GeoLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BootstrapLocation extends LocationCache {
	private static final Logger logger = LoggerFactory
			.getLogger(BootstrapLocation.class);

	private BootstrapLocationDao bootstrapLocationDao;

	public BootstrapLocation(GeoLocator locator, BootstrapLocationDao bootstrapLocationDao) {
		super(locator);
		this.bootstrapLocationDao = bootstrapLocationDao;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		logger.info("Bootstrap :: Coordinates");
		bootstrapCoordinates();
		
		logger.info("Bootstrap :: Keywords");
		bootstrapKeywords();
	}

	private void bootstrapCoordinates() {
		List<String> strs = bootstrapLocationDao.fetchPopularCitiesCoordinates();
		for (String latlng : strs) {
			String[] strings = latlng.split(",");
			Double lat = Double.parseDouble(strings[0]);
			Double lng = Double.parseDouble(strings[1]);
			findByCoordinates(lat, lng);
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
            findByKeyword(keyword);
	}
}
