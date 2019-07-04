package com.springframework.entity;

import com.google.gson.annotations.SerializedName;

public class WeatherForecast {
    public static final String OPEN_WEATHER_API_URL = "https://api.openweathermap.org/";
    public static final String APPID = "c9a98f66eca9b228e202a2c743b9be1f";
    public static final String UNITS = "metric";
    public final double temp;

    public double getTemp() {
        return temp;
    }
    public WeatherForecast(double temp) {
        this.temp = temp;

    }
}
