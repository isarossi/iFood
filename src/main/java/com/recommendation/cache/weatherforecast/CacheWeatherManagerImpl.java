package com.recommendation.cache.weatherforecast;

import com.recommendation.cache.weatherforecast.model.Weather;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

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
        hashOperations.put("Redis","A",weather);
        valueOperations.set("A", weather, 1, TimeUnit.HOURS);
    }

    @Override
    public Weather get(String key) {
        return (Weather) hashOperations.get("Redis","A");
    }


}
