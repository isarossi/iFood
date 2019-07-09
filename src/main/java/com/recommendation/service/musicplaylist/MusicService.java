package com.recommendation.service.musicplaylist;

import com.recommendation.properties.AuthorizationProperties;
import com.recommendation.properties.MusicRecommendationProperties;
import com.recommendation.Constants;
import com.recommendation.error.RestException;
import com.recommendation.properties.RedisProperties;
import com.recommendation.service.musicplaylist.model.PlaylistJsonResponse;
import com.recommendation.service.musicplaylist.model.TokenJsonResponse;
import com.recommendation.service.weatherforecast.model.WeatherForecastJsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Service
public class MusicService {

    private final MusicRecommendationProperties musicRecConfig;
    private final AuthorizationProperties authorizationProperties;

    @Autowired
    public MusicService(AuthorizationProperties authorizationProperties, MusicRecommendationProperties musicRecConfig, RedisProperties redisProp) {
        this.musicRecConfig = musicRecConfig;
        this.authorizationProperties = authorizationProperties;
    }

    public PlaylistJsonResponse retrievePlaylistRecommendation(String genre) throws IOException {
        AuthorizationService authorizationService = new AuthorizationService(authorizationProperties);
        TokenJsonResponse tokenJsonResponse = authorizationService.retrieveToken();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(musicRecConfig.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        MusicPlaylistServiceInterface musicPlaylistService = retrofit.create(MusicPlaylistServiceInterface.class);
        Call<PlaylistJsonResponse> call = musicPlaylistService.getRecommendation(genre, musicRecConfig.getLimit(), tokenJsonResponse.getTokenType() + Constants.SPACE + tokenJsonResponse.getAccessToken());
        Response<PlaylistJsonResponse> playlistResponse = executeWeatherForecastService(call);
        return playlistResponse.body();
    }

    private Response<PlaylistJsonResponse> executeWeatherForecastService(Call<PlaylistJsonResponse> call) throws IOException {
        Response<PlaylistJsonResponse> playlistResponse = null;
        try {
            playlistResponse = call.execute();
            if (!playlistResponse.isSuccessful()) {
                throw new RestException(playlistResponse.errorBody().string());
            }
        } catch (IOException e) {
            throw e;
        }
        return playlistResponse;
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
