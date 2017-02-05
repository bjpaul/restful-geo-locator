package org.locator.geo.rest.controller;


import org.locator.geo.rest.request.dto.LocationList;
import org.locator.geo.rest.request.util.Http;
import org.locator.geo.rest.response.dto.AbstractResponseDto;
import org.locator.geo.rest.response.util.ResponseUtil;
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
	private Http http;
	
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public HttpEntity<AbstractResponseDto> search(
    		@RequestParam(name="lat", required=false) Double lat,
    		@RequestParam(name="lng", required=false) Double lng,
    		@RequestParam(name="q", required=false) String keyword)  {
    	try{
    		String ip = http.getClientIP();
    		
	    	LocationList list = locationService.geoLocation(lat, lng, keyword, ip);
	    	
	    	if(list != null){
	    		return ResponseUtil.success().body(list).message("Location list fetched successfully !!!").send(HttpStatus.OK);
	    	}else{
	    		return ResponseUtil.error().message("No data found !!!").send(HttpStatus.NOT_FOUND);
	    	}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		return null;
    	}
    }

	
}
