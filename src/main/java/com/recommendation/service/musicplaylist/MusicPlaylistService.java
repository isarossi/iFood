package com.recommendation.service.musicplaylist;

import com.recommendation.properties.PlaylistApiProperties;
import com.recommendation.service.Constants;
import com.recommendation.service.weatherforecast.WeatherForecastResponse;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Base64;


public class MusicPlaylistService{
    @Autowired
    private static PlaylistApiProperties playListApiProps;

    public static MusicPlaylistServiceInterface createService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(playListApiProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(MusicPlaylistServiceInterface.class);
    }

    public String retrievePlaylistByGenre(String genre) throws IOException {
        Call<TokenResponse> call = createService().getAccessToken(playListApiProps.getGrantType(), retrieveAuthorizationEncoded());
        TokenResponse authorizationResponse = call.execute().body();
        return "Oi";
    }

    private String retrieveAuthorizationEncoded() {
        String credentials = playListApiProps.getClientId() + Constants.COLON + playListApiProps.getClientSecret();
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
