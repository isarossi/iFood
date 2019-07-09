package com.recommendation.service.musicplaylist;

import com.recommendation.Constants;
import com.recommendation.properties.AuthorizationProperties;
import com.recommendation.error.RestException;
import com.recommendation.service.musicplaylist.model.TokenJsonResponse;
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
    private final AuthorizationProperties authorizationProperties;

    @Autowired
    public AuthorizationService(AuthorizationProperties authorizationProperties) {
        this.authorizationProperties = authorizationProperties;
    }

    public TokenJsonResponse retrieveToken() throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(authorizationProperties.getUrl()).addConverterFactory(GsonConverterFactory.create()).build();
        AuthorizationServiceInterface tokenService = retrofit.create(AuthorizationServiceInterface.class);
        Call<TokenJsonResponse> call = tokenService.getAccessToken(authorizationProperties.getGrantType(), authorizationProperties.getAuthorizationPrefix() + Constants.SPACE + retrieveAuthorizationEncoded());
        Response<TokenJsonResponse> tokenResponse = executeAuthorizationService(call);
        return tokenResponse.body();
    }

    private String retrieveAuthorizationEncoded() {
        String credentials = authorizationProperties.getClientId() + Constants.COLON + authorizationProperties.getClientSecret();
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    private Response<TokenJsonResponse> executeAuthorizationService(Call<TokenJsonResponse> call) throws IOException {
        Response<TokenJsonResponse> tokenResponse = null;
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
