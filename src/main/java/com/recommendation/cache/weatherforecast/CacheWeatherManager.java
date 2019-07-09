package com.recommendation.cache.weatherforecast;

import com.recommendation.cache.weatherforecast.model.Weather;

public interface CacheWeatherManager {
    void save(Weather weather);
    Weather get(String key);

}
