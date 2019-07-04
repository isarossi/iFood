package com.springframework.entity;

import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface OpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherForecast> getWeatherForecast(@Query("APPID") String appid,@Query("units") String units,@Query("q") String city);
}
