package com.recommendation.service.weatherforecast;

import com.recommendation.cache.weatherforecast.CacheWeatherManager;
import com.recommendation.cache.weatherforecast.CacheWeatherManagerImpl;
import com.recommendation.cache.weatherforecast.model.Weather;
import com.recommendation.error.RestException;
import com.recommendation.properties.WeatherProperties;
import com.recommendation.service.weatherforecast.model.WeatherForecastJsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Service
public class WeatherService {
    private final WeatherProperties weatherProps;
    private CacheWeatherManager weatherCache;

    @Autowired
    public WeatherService(WeatherProperties weatherProps, CacheWeatherManager weatherCache) {
        this.weatherProps = weatherProps;
        this.weatherCache = weatherCache;
    }

    public WeatherForecastJsonResponse retrieveWeatherResponse(String city) throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        OpenWeatherServiceInterface openWeatherService = retrofit.create(OpenWeatherServiceInterface.class);
        Call<WeatherForecastJsonResponse> call = openWeatherService.getWeatherForecastByCity(weatherProps.getAppid(), weatherProps.getUnits(), city);
        Response<WeatherForecastJsonResponse> weatherForecastService = executeWeatherForecastService(call);
        Weather w = new Weather(weatherForecastService.body());
        weatherCache.save(w);
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
