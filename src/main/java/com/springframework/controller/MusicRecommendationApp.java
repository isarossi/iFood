package com.springframework.controller;

import com.springframework.entity.OpenWeather;
import com.springframework.entity.WeatherForecast;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;

import java.util.List;

@SpringBootApplication
@RestController
public class MusicRecommendationApp {

        @RequestMapping("/")
        public String home() throws IOException {
            //Create a very simple REST adapter which points the GitHub API.
            Retrofit retrofit = new Retrofit.Builder().baseUrl(WeatherForecast.OPEN_WEATHER_API_URL).addConverterFactory(GsonConverterFactory.create()).build();
            // Create an instance of our GitHub API interface.
            OpenWeather openWeatherService = retrofit.create(OpenWeather.class);
            // Create a call instance for looking up Retrofit contributors.
            Call<WeatherForecast> call = openWeatherService.getWeatherForecast(WeatherForecast.APPID, WeatherForecast.UNITS,"London");

            // Fetch and print a list of the contributors to the libr
            // ary.
            WeatherForecast weatherForecast = call.execute().body();
           // weatherForecastList.forEach(item->System.out.println(item.temp));
            return "Hello Docker World";
        }
        public static void main(String[] args) {

            SpringApplication.run(MusicRecommendationApp.class, args);
        }

}
