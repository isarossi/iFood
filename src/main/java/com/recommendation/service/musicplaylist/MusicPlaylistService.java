package com.recommendation.service.musicplaylist;

import com.recommendation.properties.PlaylistApiProperties;
import com.recommendation.service.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class MusicPlaylistService{
    @Autowired
    private PlaylistApiProperties playListApiProps;

    private MusicPlaylistInterface service;

    public MusicPlaylistService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(playListApiProps.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(MusicPlaylistInterface.class);
    }

    public List<String> retrieveRecommendation(Double temperature) throws IOException {
        List<String> playlist = new ArrayList();
        Call<TokenResponse> call = service.getAccessToken(playListApiProps.getGrantType(), retrieveAuthorizationEncoded());
        TokenResponse authorizationResponse = call.execute().body();
        return playlist;
    }

    private String retrieveAuthorizationEncoded() {
        String credentials = playListApiProps.getClientId() + Constants.COLON + playListApiProps.getClientSecret();
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
