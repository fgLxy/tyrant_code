package org.tyrant.redis.spring;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.StringUtils;
import org.tyrant.core.utils.JSONUtils;
import org.tyrant.redis.service.RedisService;

public class RedisCache implements Cache {
	RedisService service;
	
	private String name;
	
	private int secounds;
	
	Logger log = LoggerFactory.getLogger(RedisCache.class);
	
	/**
	 * 
	 * @param name 缓存名
     * @param time 过期时间，单位是秒。
	 */
	public RedisCache(RedisService service, String name, int secounds) {
		this.service = service;
		this.name = name;
		this.secounds = secounds;
	}

	@Override
	public String getName() {
		return this.name;
	}
	

	private String serializer(Object obj) {
		String className = obj.getClass().getCanonicalName();
		try {
			String jsonValue = JSONUtils.toJson(obj);
			return className + "@" + jsonValue;
		} catch (Throwable t) {
			log.warn("{}缓存序列化失败.输入为:{}", obj);
		}
		return obj.toString();
	}
	
	@SuppressWarnings("unchecked")
	private <T> T deserializer(String value) {
		int split = value.indexOf("@");
		if (split <= 0) {
			return (T) value;
		}
		String className = value.substring(0, split);
		String jsonValue = value.substring(split + 1);
		try {
			return (T) JSONUtils.parse(jsonValue, Class.forName(className));
		} catch (ClassNotFoundException e) {
			log.warn("{}缓存反序列化失败.redis返回串为:{}", this.name, value);
			
		}
		return (T) value;
	}
	
	@Override
	public Object getNativeCache() {
		return service;
	}

	@Override
	public ValueWrapper get(Object key) {
		String value = service.get(this.serializer(key));
		return StringUtils.isEmpty(value) ? null : new SimpleValueWrapper(deserializer(value));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Class<T> type) {
		ValueWrapper wrapper = this.get(key);
		return wrapper == null ? null : (T) wrapper.get();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		ValueWrapper result = get(key);

		if (result != null) {
			return (T) result.get();
		}

		T value = valueFromLoader(key, valueLoader);
		put(key, value);
		return value;
	}
	
	@Override
	public void put(Object key, Object value) {
		service.set(this.serializer(key), serializer(value));
		service.expire(this.serializer(key), this.secounds);
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		ValueWrapper existingValue = this.get(key);
		 if (existingValue == null) {
		     this.put(key, value);
		     return null;
		 } else {
		     return existingValue;
		 }
	}

	@Override
	public void evict(Object key) {
		service.del(this.serializer(key));
	}

	@Override
	public void clear() {
	}
	
	private static <T> T valueFromLoader(Object key, Callable<T> valueLoader) {
		try {
			return valueLoader.call();
		} catch (Exception e) {
			throw new ValueRetrievalException(key, valueLoader, e);
		}
	}

}
