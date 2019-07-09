package com.recommendation.cache.weatherforecast;

import com.recommendation.cache.weatherforecast.model.Weather;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
public class CacheWeatherManagerImpl implements CacheWeatherManager {

    private RedisTemplate redisTemplate;
    private HashOperations hashOperations;
    private ValueOperations valueOperations;

    public CacheWeatherManagerImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void save(Weather weather) {
        valueOperations.set(weather.getCity(),weather);
        setExpireTime(weather.getCity(), 1, TimeUnit.HOURS);
        valueOperations.set(weather.retrieveCoordinateKey(),weather);
        setExpireTime(weather.retrieveCoordinateKey(), 1, TimeUnit.HOURS);
    }

    @Override
    public Object get(String key) {
        return valueOperations.get(key);
    }

    @Override
    public boolean hasEntry(String key) {
        return (get(key) != null);
    }

    public void setExpireTime(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }
}
