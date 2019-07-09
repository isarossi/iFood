package com.recommendation.service.weatherforecast;

import com.recommendation.cache.weatherforecast.CacheWeatherManager;
import com.recommendation.cache.weatherforecast.model.Weather;
import com.recommendation.error.RestException;
import com.recommendation.properties.WeatherProperties;
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
    private final WeatherProperties weatherProps;
    private CacheWeatherManager weatherCache;

    @Autowired
    public WeatherService(WeatherProperties weatherProps, CacheWeatherManager weatherCache) {
        this.weatherProps = weatherProps;
        this.weatherCache = weatherCache;
    }

    private OpenWeatherServiceInterface Init() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(weatherProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(OpenWeatherServiceInterface.class);
    }

    public double retrieveWeatherResponse(String city) throws IOException {
        double temp;
        if (city != null && weatherCache.hasEntry(city.toLowerCase())) {
            Weather weather = (Weather) weatherCache.get(city.toLowerCase());
            temp = weather.getTemp();
        } else {
            OpenWeatherServiceInterface openWeatherService = Init();
            Call<WeatherForecastJsonResponse> call = openWeatherService.getWeatherForecastByCity(weatherProps.getAppid(), weatherProps.getUnits(), city);
            Response<WeatherForecastJsonResponse> weatherForecastService = executeService(call);
            temp = weatherForecastService.body().getMain().getTemp();
        }
        return temp;
    }

    public double retrieveWeatherResponse(String lat, String lon) throws IOException {
        double temp;
        if (lat != null && lon != null) {
            Weather weather = (Weather) weatherCache.get(Weather.retrieveCoordinateKey(lat, lon));
            temp = weather.getTemp();
        } else {
            OpenWeatherServiceInterface openWeatherService = Init();
            Call<WeatherForecastJsonResponse> call = openWeatherService.getWeatherForecastByCoordinates(weatherProps.getAppid(), weatherProps.getUnits(), lat, lon);
            Response<WeatherForecastJsonResponse> weatherForecastService = executeService(call);
            temp = weatherForecastService.body().getMain().getTemp();
        }
        return temp;
    }

    private Response<WeatherForecastJsonResponse> executeService(Call<WeatherForecastJsonResponse> call) throws IOException {
        Response<WeatherForecastJsonResponse> weatherForecastService = executeWeatherForecastService(call);
        Weather weather = new Weather(weatherForecastService.body());
        weatherCache.save(weather);
        return weatherForecastService;
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
