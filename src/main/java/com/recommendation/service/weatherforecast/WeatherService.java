package com.recommendation.service.weatherforecast;

import com.recommendation.properties.WeatherApiProperties;
import com.recommendation.service.musicplaylist.MusicPlaylistInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Service
public class WeatherService {

    @Autowired
    private static WeatherApiProperties weatherProps;
    private OpenWeatherServiceInterface service;

    public WeatherService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(OpenWeatherServiceInterface.class);
    }
    public double retrieveTemperatureByCity(String city)  throws IOException {
        Call<WeatherForecastResponse> call = service.getWeatherForecastByCity(weatherProps.getAppid(), weatherProps.getUnits(), city);
        WeatherForecastResponse weatherForecast = call.execute().body();
        return weatherForecast.getMain().getTemp();
    }

    public double  retrieveTemperatureByCoordinates(String lat, String lon) throws IOException {
        Call<WeatherForecastResponse> call = service.getWeatherForecastByCoordinates(weatherProps.getAppid(), weatherProps.getUnits(), lat, lon);
        WeatherForecastResponse weatherForecast = call.execute().body();
        return weatherForecast.getMain().getTemp();
    }
}
