package com.recommendation.cache.weatherforecast;

import java.util.Objects;

public class WeatherCache {
    String city;
    double lat;
    double lon;
    double temp;

    public WeatherCache(String city, double lat, double lon, double temp) {
        this.city = city;
        this.lat = lat;
        this.lon = lon;
        this.temp = temp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, lat, lon);
    }

    public String getStringHashCode(){
        return Integer.toString(hashCode());
    }

    public String getRedisKey() {
        return WeatherCache.class.getName();
    }
}
