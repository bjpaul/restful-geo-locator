package org.locator.geo.rest.controller;

import org.locator.geo.rest.cache.CacheNames;
import org.locator.geo.rest.cache.manage.LocalCacheManager;
import org.locator.geo.rest.response.dto.AbstractResponseDto;
import org.locator.geo.rest.response.util.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

	private LocalCacheManager cacheManeger;
	
	@Autowired
	private HttpResponse httpResponse;
	
    @RequestMapping(value = "/cache/search", method = RequestMethod.GET)
    public HttpEntity<AbstractResponseDto> search(@RequestParam(name="q") String keyword)  {
    	try{
    		Object o =cacheManeger.fetchDataFromCache(CacheNames.KEYWORD, keyword);
    		if(o != null){
	    		return httpResponse.success().body(o).message("Location list fetched from cache successfully !!!").send(HttpStatus.OK);
	    	}else{
	    		return httpResponse.error().message("No data found !!!").send(HttpStatus.NOT_FOUND);
	    	}
    	}catch(Exception ex){
    		ex.printStackTrace();
    		return null;
    	}
    }
}
