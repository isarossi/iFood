package com.recommendation.service.musicplaylist;

import com.recommendation.properties.AuthorizationConfig;
import com.recommendation.properties.MusicRecommendationConfig;
import com.recommendation.Constants;
import com.recommendation.service.RestException;
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
import java.util.Base64;

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
        Call<PlaylistResponse> call = musicPlaylistService.getRecommendation(genre, musicRecConfig.getLimit(), tokenResponse.getTokenType() + Constants.SPACE + tokenResponse.getAccessToken());
        Response<PlaylistResponse> playlistResponse = executeWeatherForecastService(call);
        return playlistResponse.body();
    }

    private Response<PlaylistResponse> executeWeatherForecastService(Call<PlaylistResponse> call) {
        Response<PlaylistResponse> playlistResponse = null;
        try {
            playlistResponse = call.execute();
            if (!playlistResponse.isSuccessful()) {
                throw new RestException(playlistResponse.errorBody().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
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
