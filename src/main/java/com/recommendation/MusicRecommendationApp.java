package com.recommendation;

import com.recommendation.Service.weatherforecast.OpenWeather;
import com.recommendation.Service.weatherforecast.WeatherForecast;
import com.recommendation.Service.weatherforecast.WeatherForecastResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class MusicRecommendationApp {


        @RequestMapping(value = "/city")
        public double reccomendationByCity(String city) throws IOException {
            List<String> params = new ArrayList<String>();
            params.add(city);
            //Create a very  REST adapter which points the OpenWeather API.
            Retrofit retrofit = new Retrofit.Builder().baseUrl(WeatherForecast.OPEN_WEATHER_API_URL).addConverterFactory(GsonConverterFactory.create()).build();
            // Create an instance of our OpenWeather API interface.
            OpenWeather openWeatherService = retrofit.create(OpenWeather.class);
            // Create a call instance for looking up Retrofit WeatherForecastResponse.
            Call<WeatherForecastResponse> call = openWeatherService.getWeatherForecast(WeatherForecast.APPID, WeatherForecast.UNITS, params);
            // Fetch weather
            WeatherForecastResponse weatherForecast = call.execute().body();
           // weatherForecastList.forEach(item->System.out.println(item.temp));
            return weatherForecast.getMain().getTemp();
        }

        @RequestMapping(value = {"/lat", "/lon"})
        public double reccomendationByCoordenates(String lat, String lon) throws IOException {
            List<String> params = new ArrayList<String>();
            params.add(lat);
            params.add(lon);
            //Create a very  REST adapter which points the OpenWeather API.
            Retrofit retrofit = new Retrofit.Builder().baseUrl(WeatherForecast.OPEN_WEATHER_API_URL).addConverterFactory(GsonConverterFactory.create()).build();
            // Create an instance of our OpenWeather API interface.
            OpenWeather openWeatherService = retrofit.create(OpenWeather.class);
            // Create a call instance for looking up Retrofit WeatherForecastResponse.
            Call<WeatherForecastResponse> call = openWeatherService.getWeatherForecast(WeatherForecast.APPID, WeatherForecast.UNITS,params);
            // Fetch weather
            WeatherForecastResponse weatherForecast = call.execute().body();
            // weatherForecastList.forEach(item->System.out.println(item.temp));
            return weatherForecast.getMain().getTemp();
    }
        public static void main(String[] args) {

            SpringApplication.run(MusicRecommendationApp.class, args);
        }

}
