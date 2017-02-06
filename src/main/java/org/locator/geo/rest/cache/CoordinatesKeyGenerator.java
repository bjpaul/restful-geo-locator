package org.locator.geo.rest.cache;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

@Component("coordinatesKey")
public class CoordinatesKeyGenerator implements KeyGenerator {
	private static final Logger logger = LoggerFactory.getLogger(CoordinatesKeyGenerator.class);

	@Override
	public Object generate(Object target, Method method, Object... params) {
        String lat = String.format("%1.2f", params[0]);
        String lng = String.format("%1.2f", params[1]);
        String latlng = lat+","+lng;
        logger.info("Generating key :: "+latlng);
        return latlng;
	}

}
