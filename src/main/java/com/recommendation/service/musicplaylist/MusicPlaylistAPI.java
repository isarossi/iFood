package com.recommendation.service.musicplaylist;

import com.recommendation.properties.PlaylistApiProperties;
import com.recommendation.service.weatherforecast.OpenWeatherService;
import com.recommendation.service.weatherforecast.WeatherForecastResponse;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MusicPlaylistAPI {
   // @Autowired
   //private PlaylistApiProperties playListApiProps;

  //  public String authorize(){
       // Retrofit retrofit = new Retrofit.Builder().baseUrl(playListApiProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
      //  MusicPlaylistService musicPlaylistService = retrofit.create(MusicPlaylistService.class);
      //  Call<AuthorizationResponse> call = musicPlaylistService.getAuthorization(playListApiProps.getClientId(),playListApiProps.getResType(),playListApiProps.getCallback(),playListApiProps.getScope());
      //  AuthorizationResponse authorizationResponse = call.execute().body();
   // }
}
