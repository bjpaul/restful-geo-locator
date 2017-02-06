package org.locator.geo.rest.cache;

public class CacheNames {

	public final static String KEYWORD = "locationBykeyword";
	public final static String LATLNG = "locationByLatLng";
	public final static String IP = "cityByIp";
	public final static String GEOID = "locationByGeonameId";
	
	public static String[] supportedNames(){
		String[] strs = { KEYWORD,LATLNG, IP, GEOID };
		return strs;
	}
}
