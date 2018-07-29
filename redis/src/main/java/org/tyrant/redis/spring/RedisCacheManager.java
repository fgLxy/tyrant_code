package org.tyrant.redis.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheManager extends AbstractCacheManager implements ApplicationContextAware {
	
	List<RedisCache> caches = new ArrayList<>();
	
	ApplicationContext appContext;
	
	Logger log = LoggerFactory.getLogger(RedisCacheManager.class);
	
	@Override
	protected Collection<? extends Cache> loadCaches() {
		return this.caches;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.appContext = applicationContext;
		Map<String, RedisCache> cacheMap = this.appContext.getBeansOfType(RedisCache.class);
		if (cacheMap == null) {
			return;
		}
		this.caches.addAll(cacheMap.values());
		for (RedisCache cache : this.caches) {
			log.info("缓存名:{}", cache.getName());
		}
	}

}
