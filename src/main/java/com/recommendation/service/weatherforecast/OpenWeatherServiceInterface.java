package com.recommendation.service.weatherforecast;

import com.recommendation.service.weatherforecast.model.WeatherForecastResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherServiceInterface {
    @GET("data/2.5/weather")
    Call<WeatherForecastResponse> getWeatherForecastByCity(@Query("APPID") String appid,
                                                           @Query("units") String units,
                                                           @Query("q") String city);

    @GET("data/2.5/weather")
    Call<WeatherForecastResponse> getWeatherForecastByCoordinates(@Query("APPID") String appid,
                                                                  @Query("units") String units,
                                                                  @Query("lat") String lat,
                                                                  @Query("lon") String lon);
}
