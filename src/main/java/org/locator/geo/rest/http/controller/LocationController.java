package org.locator.geo.rest.http.controller;


import org.locator.geo.rest.http.request.dto.LocationList;
import org.locator.geo.rest.http.request.util.HttpRequest;
import org.locator.geo.rest.http.response.dto.AbstractResponseDto;
import org.locator.geo.rest.http.response.util.HttpResponse;
import org.locator.geo.rest.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LocationController {
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private HttpRequest httpRequest;
	
	@Autowired
	private HttpResponse httpResponse;
	
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public HttpEntity<AbstractResponseDto> search(
    		@RequestParam(name="lat", required=false) Double lat,
    		@RequestParam(name="lng", required=false) Double lng,
    		@RequestParam(name="q", required=false) String keyword)  {
    	try{
    		String ip = httpRequest.getClientIP();
    		
	    	LocationList list = locationService.geoLocation(lat, lng, keyword, ip);
	    	
	    	if(list != null){
	    		return httpResponse.success().body(list).message("Location list fetched successfully !!!").send(HttpStatus.OK);
	    	}else{
	    		return httpResponse.error().message("No data found !!!").send(HttpStatus.NOT_FOUND);
	    	}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		return null;
    	}
    }

	
}
