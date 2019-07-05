package com.recommendation.service.weatherforecast;

import com.recommendation.properties.WeatherApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class WeatherAPI {
    @Autowired
    private WeatherApiProperties weatherProps;
    private Retrofit retrofit;
    OpenWeatherService openWeatherService;

    public WeatherAPI(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        OpenWeatherService openWeatherService = retrofit.create(OpenWeatherService.class);
    }

    public double retrieveTemperatureByCity(String city)  throws IOException {
        Call<WeatherForecastResponse> call = openWeatherService.getWeatherForecastByCity(weatherProps.getAppid(), weatherProps.getUnits(), city);
        WeatherForecastResponse weatherForecast = call.execute().body();
        return weatherForecast.getMain().getTemp();
    }

    public double  retrieveTemperatureByCoordinates(String lat, String lon) throws IOException {
        Call<WeatherForecastResponse> call = openWeatherService.getWeatherForecastByCoordinates(weatherProps.getAppid(), weatherProps.getUnits(), lat, lon);
        WeatherForecastResponse weatherForecast = call.execute().body();
        return weatherForecast.getMain().getTemp();
    }


}
