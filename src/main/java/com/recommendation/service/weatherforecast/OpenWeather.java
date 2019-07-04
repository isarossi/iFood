package com.recommendation.service.weatherforecast;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherForecastResponse> getWeatherForecast(@Query("APPID") String appid,@Query("units") String units, @Query("params") List<String> param);
}
