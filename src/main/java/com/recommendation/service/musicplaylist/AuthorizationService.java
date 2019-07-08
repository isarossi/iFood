package com.recommendation.service.musicplaylist;

import com.recommendation.service.ServiceConstants;
import com.recommendation.properties.AuthorizationConfig;
import com.recommendation.service.errorhandling.RestException;
import com.recommendation.service.musicplaylist.model.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Base64;

@Service
public class AuthorizationService {
    private final AuthorizationConfig authorizationConfig;

    @Autowired
    public AuthorizationService(AuthorizationConfig authorizationConfig) {
        this.authorizationConfig = authorizationConfig;
    }

    public TokenResponse retrieveToken() throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(authorizationConfig.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        AuthorizationServiceInterface tokenService = retrofit.create(AuthorizationServiceInterface.class);
        Call<TokenResponse> call = tokenService.getAccessToken(authorizationConfig.getGrantType(), authorizationConfig.getAuthorizationPrefix() + ServiceConstants.SPACE + retrieveAuthorizationEncoded());
        Response<TokenResponse> tokenResponse = executeAuthorizationService(call);
        return tokenResponse.body();
    }

    private String retrieveAuthorizationEncoded() {
        String credentials = authorizationConfig.getClientId() + ServiceConstants.COLON + authorizationConfig.getClientSecret();
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    private Response<TokenResponse> executeAuthorizationService(Call<TokenResponse> call) throws IOException {
        Response<TokenResponse> tokenResponse = null;
        try {
            tokenResponse = call.execute();
            if (!tokenResponse.isSuccessful()) {
                throw new RestException(tokenResponse.errorBody().string());
            }
        } catch (IOException e) {
            throw e;
        }
        return tokenResponse;
    }
}
