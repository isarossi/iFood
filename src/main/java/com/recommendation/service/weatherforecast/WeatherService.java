package com.recommendation.service.weatherforecast;

import com.recommendation.properties.WeatherConfig;
import com.recommendation.service.weatherforecast.model.WeatherForecastJsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Service
public class WeatherService {

    private final WeatherConfig weatherProps;

    @Autowired
    public WeatherService(WeatherConfig weatherProps) {
        this.weatherProps = weatherProps;
    }

    public WeatherForecastJsonResponse retrieveWeatherResponse(String city){
        WeatherForecastJsonResponse weatherForecast = null;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        OpenWeatherServiceInterface openWeatherService = retrofit.create(OpenWeatherServiceInterface.class);
        Call<WeatherForecastJsonResponse> call = openWeatherService.getWeatherForecastByCity(weatherProps.getAppid(), weatherProps.getUnits(), city);
        try{
             weatherForecast = call.execute().body();
        }catch(IOException ex){
            ex.printStackTrace();
        }

        return weatherForecast;
    }

    public WeatherForecastJsonResponse retrieveWeatherResponse(String lat, String lon) throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        OpenWeatherServiceInterface openWeatherService = retrofit.create(OpenWeatherServiceInterface.class);
        Call<WeatherForecastJsonResponse> call = openWeatherService.getWeatherForecastByCoordinates(weatherProps.getAppid(), weatherProps.getUnits(), lat, lon);
        WeatherForecastJsonResponse weatherForecast = call.execute().body();
        return weatherForecast;
    }

}
