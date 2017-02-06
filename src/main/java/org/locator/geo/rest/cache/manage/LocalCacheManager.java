package org.locator.geo.rest.cache.manage;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.locator.geo.rest.cache.CacheNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.stereotype.Component;

@Component("localCache")
public class LocalCacheManager extends AbstractCacheManager{
	
	private static final Logger logger = LoggerFactory.getLogger(LocalCacheManager.class);
	
	private Map<String, ConcurrentMapCache> caches = new ConcurrentHashMap<String, ConcurrentMapCache>();

	
	public LocalCacheManager() {
		this.caches.put(CacheNames.KEYWORD, new ConcurrentMapCache(CacheNames.KEYWORD));
		this.caches.put(CacheNames.LATLNG, new ConcurrentMapCache(CacheNames.LATLNG));
		this.caches.put(CacheNames.IP, new ConcurrentMapCache(CacheNames.IP));
		this.caches.put(CacheNames.GEOID, new ConcurrentMapCache(CacheNames.GEOID));
	}

	@Override
	protected Collection<? extends Cache> loadCaches() {
		logger.info("Loading all caches .....");
		return this.caches.values();
	}

	public Object fetchDataFromCache(String cacheName, Object key){
		ConcurrentMapCache cache = caches.get(cacheName);
		if(cache.get(key) != null){
			return cache.get(key).get();
		}
		return null;
	}
}
