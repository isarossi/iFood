package com.recommendation.cache.weatherforecast.model;

import com.recommendation.service.weatherforecast.model.WeatherForecastJsonResponse;

import java.io.Serializable;

public class Weather {
    private double temp;
    private String city;
    private double lat;
    private double lon;

    public Weather(double temp, String city, double lat, double lon) {
        this.temp = temp;
        this.city = city;
        this.lat = lat;
        this.lon = lon;
    }

    public Weather(WeatherForecastJsonResponse body) {
        this.temp = body.getMain().getTemp();
        this.city = body.getCityName().toLowerCase();
        this.lat = body.getCoord().getLat();
        this.lon =  body.getCoord().getLon();
    }

    public static String retrieveCoordinateKey(String lat, String lon){
        return lat+":"+lon;
    }

    public String retrieveCoordinateKey(){
        return String.valueOf(this.lat)+":"+String.valueOf(this.lon);
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

}
