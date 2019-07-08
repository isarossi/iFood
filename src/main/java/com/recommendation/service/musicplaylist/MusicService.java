package com.recommendation.service.musicplaylist;

import com.recommendation.properties.AuthorizationConfig;
import com.recommendation.properties.MusicRecommendationConfig;
import com.recommendation.service.ServiceConstants;
import com.recommendation.service.errorhandling.RestException;
import com.recommendation.service.musicplaylist.model.PlaylistResponse;
import com.recommendation.service.musicplaylist.model.TokenResponse;
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

    private final MusicRecommendationConfig musicRecConfig;
    private final AuthorizationConfig authorizationConfig;


    @Autowired
    public MusicService(AuthorizationConfig authorizationConfig, MusicRecommendationConfig musicRecConfig) {
        this.musicRecConfig=musicRecConfig;
        this.authorizationConfig=authorizationConfig;
    }

    public PlaylistResponse retrievePlaylistRecommendation(String genre) throws IOException {
        AuthorizationService authorizationService = new AuthorizationService(authorizationConfig);
        TokenResponse tokenResponse = authorizationService.retrieveToken();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(musicRecConfig.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        MusicPlaylistServiceInterface musicPlaylistService = retrofit.create(MusicPlaylistServiceInterface.class);
        Call<PlaylistResponse> call = musicPlaylistService.getRecommendation(genre, musicRecConfig.getLimit(), tokenResponse.getTokenType() + ServiceConstants.SPACE + tokenResponse.getAccessToken());
        Response<PlaylistResponse> playlistResponse = executeWeatherForecastService(call);
        return playlistResponse.body();
    }

    private Response<PlaylistResponse> executeWeatherForecastService(Call<PlaylistResponse> call) throws IOException {
        Response<PlaylistResponse> playlistResponse = null;
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
            if (temp > ServiceConstants.MAX_TEMPERATURE) {
                recommendation = ServiceConstants.MUSIC_GENRE_PARTY;
            } else if (temp < ServiceConstants.MIN_TEMPERATURE) {
                recommendation = ServiceConstants.MUSIC_GENRE_CLASSICAL;
            } else if (temp >= ServiceConstants.FIFTEEN_CELSIUS_TEMPERATURE && temp <= ServiceConstants.MAX_TEMPERATURE) {
                recommendation = ServiceConstants.MUSIC_GENRE_POP;
            } else if (temp >= ServiceConstants.MIN_TEMPERATURE && temp < ServiceConstants.FIFTEEN_CELSIUS_TEMPERATURE) {
                recommendation = ServiceConstants.MUSIC_GENRE_ROCK;
            }
        }
        return recommendation;
    }
}
