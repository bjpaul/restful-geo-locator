package org.locator.geo.rest.config;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.locator.geo.rest.model.Location;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.record.City;

@Transactional(readOnly= true)
public interface GeoLocator extends InitializingBean{
	List<Location> findByKeyword(String keyword); 
	List<Location> findByCoordinates(Double latitude, Double longitude);
	City findByIP(String ip) throws UnknownHostException, IOException, GeoIp2Exception;
	List<Location> findByGeoLocationId(Integer id);

}
