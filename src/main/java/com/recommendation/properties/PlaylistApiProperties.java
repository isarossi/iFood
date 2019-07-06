package com.recommendation.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "music")
public class PlaylistApiProperties {
    protected String authorizationPrefix;
    private String url;
    private String clientSecret;
    private String clientId;
    private String grantType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getAuthorizationPrefix() {
        return authorizationPrefix;
    }

    public void setAuthorizationPrefix(String authorizationPrefix) {
        this.authorizationPrefix = authorizationPrefix;
    }
}
