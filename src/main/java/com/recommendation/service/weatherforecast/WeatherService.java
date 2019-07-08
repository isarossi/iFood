package com.recommendation.service.weatherforecast;

import com.recommendation.properties.WeatherConfig;
import com.recommendation.service.errorhandling.RestException;
import com.recommendation.service.weatherforecast.model.WeatherForecastJsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
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

    public WeatherForecastJsonResponse retrieveWeatherResponse(String city) throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        OpenWeatherServiceInterface openWeatherService = retrofit.create(OpenWeatherServiceInterface.class);
        Call<WeatherForecastJsonResponse> call = openWeatherService.getWeatherForecastByCity(weatherProps.getAppid(), weatherProps.getUnits(), city);
        Response<WeatherForecastJsonResponse> weatherForecastService = executeWeatherForecastService(call);
        return weatherForecastService.body();
    }

    public WeatherForecastJsonResponse retrieveWeatherResponse(String lat, String lon) throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        OpenWeatherServiceInterface openWeatherService = retrofit.create(OpenWeatherServiceInterface.class);
        Call<WeatherForecastJsonResponse> call = openWeatherService.getWeatherForecastByCoordinates(weatherProps.getAppid(), weatherProps.getUnits(), lat, lon);
        Response<WeatherForecastJsonResponse> weatherForecastService = executeWeatherForecastService(call);
        return weatherForecastService.body();
    }

    private Response<WeatherForecastJsonResponse> executeWeatherForecastService(Call<WeatherForecastJsonResponse> call) throws IOException {
        Response<WeatherForecastJsonResponse> weatherForecastService = null;
        try {
            weatherForecastService = call.execute();
            if (!weatherForecastService.isSuccessful()) {
                throw new RestException(weatherForecastService.errorBody().string());
            }
        } catch (IOException e) {
            throw e;
        }
        return weatherForecastService;
    }

}
