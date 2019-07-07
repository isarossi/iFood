package com.recommendation.service.musicplaylist;

import com.recommendation.properties.AuthorizationConfig;
import com.recommendation.properties.MusicRecommendationConfig;
import com.recommendation.Constants;
import com.recommendation.service.musicplaylist.model.PlaylistResponse;
import com.recommendation.service.musicplaylist.model.TokenResponse;
import com.recommendation.service.weatherforecast.model.WeatherForecastJsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Base64;

@Service
public class MusicService {
    private final AuthorizationConfig authorizationConfig;
    private final MusicRecommendationConfig musicRecConfig;

    @Autowired
    public MusicService(AuthorizationConfig authorizationConfig, MusicRecommendationConfig musicRecConfig) {
        this.authorizationConfig = authorizationConfig;
        this.musicRecConfig=musicRecConfig;
    }

    private TokenResponse retrieveToken() throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(authorizationConfig.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        AuthorizationServiceInterface musicPlaylistService = retrofit.create(AuthorizationServiceInterface.class);
        Call<TokenResponse> call = musicPlaylistService.getAccessToken(authorizationConfig.getGrantType(), authorizationConfig.getAuthorizationPrefix() + Constants.SPACE + retrieveAuthorizationEncoded());
        return call.execute().body();
    }

    public PlaylistResponse retrievePlaylistRecommendation(String genre) throws IOException {
        TokenResponse tokenResponse = retrieveToken();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(musicRecConfig.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        MusicPlaylistServiceInterface musicPlaylistService2 = retrofit.create(MusicPlaylistServiceInterface.class);
        Call<PlaylistResponse> call = musicPlaylistService2.getRecommendation(genre, musicRecConfig.getLimit(), tokenResponse.getTokenType() + Constants.SPACE + tokenResponse.getAccessToken());
        return call.execute().body();
    }

    private String retrieveAuthorizationEncoded() {
        String credentials = authorizationConfig.getClientId() + Constants.COLON + authorizationConfig.getClientSecret();
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    public String retrieveGenreByTemperature(WeatherForecastJsonResponse weatherForecastJsonResponse) {
        String recommendation = null;
        if (weatherForecastJsonResponse.getMain() != null && weatherForecastJsonResponse.getMain().getTemp() != null) {
            int temp = (weatherForecastJsonResponse.getMain().getTemp()).intValue();
            if (temp > Constants.MAX_TEMPERATURE) {
                recommendation = Constants.MUSIC_GENRE_PARTY;
            } else if (temp < Constants.MIN_TEMPERATURE) {
                recommendation = Constants.MUSIC_GENRE_CLASSICAL;
            } else if (temp >= Constants.FIFTEEN_CELSIUS_TEMPERATURE && temp <= Constants.MAX_TEMPERATURE) {
                recommendation = Constants.MUSIC_GENRE_POP;
            } else if (temp >= Constants.MIN_TEMPERATURE && temp < Constants.FIFTEEN_CELSIUS_TEMPERATURE) {
                recommendation = Constants.MUSIC_GENRE_ROCK;
            }
        }
        return recommendation;
    }
}
