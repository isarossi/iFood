package com.recommendation.service.weatherforecast;

import com.recommendation.properties.WeatherConfig;
import com.recommendation.service.RestException;
import com.recommendation.service.RestExceptionHandler;
import com.recommendation.service.weatherforecast.model.WeatherForecastJsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Service
public class WeatherService {

    private final WeatherConfig weatherProps;
    private static Retrofit retrofit;
    private OpenWeatherServiceInterface openWeatherService;

    @Autowired
    public WeatherService(WeatherConfig weatherProps) {
        this.weatherProps = weatherProps;
        this.retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        this.retrofit.create(OpenWeatherServiceInterface.class);
    }

    public WeatherForecastJsonResponse retrieveWeatherResponse(String city)throws IOException{
       // Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
      //  OpenWeatherServiceInterface openWeatherService = retrofit.create(OpenWeatherServiceInterface.class);
        Call<WeatherForecastJsonResponse> call = openWeatherService.getWeatherForecastByCity(weatherProps.getAppid(), weatherProps.getUnits(), city);
        Response<WeatherForecastJsonResponse> weatherForecastService = executeWeatherForecasService(call);
        return weatherForecastService.body();
    }

    public WeatherForecastJsonResponse retrieveWeatherResponse(String lat, String lon) throws IOException {
      //  Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
    //    OpenWeatherServiceInterface openWeatherService = retrofit.create(OpenWeatherServiceInterface.class);
        Call<WeatherForecastJsonResponse> call = openWeatherService.getWeatherForecastByCoordinates(weatherProps.getAppid(), weatherProps.getUnits(), lat, lon);
        Response<WeatherForecastJsonResponse> weatherForecastService = executeWeatherForecasService(call);
        return weatherForecastService.body();
    }

    private Response<WeatherForecastJsonResponse> executeWeatherForecasService(Call<WeatherForecastJsonResponse> call) {
        Response<WeatherForecastJsonResponse> weatherForecastService = null;
        try {
            weatherForecastService = call.execute();
            if (!weatherForecastService.isSuccessful()) {
                throw new RestException(weatherForecastService.errorBody().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherForecastService;
    }

}
