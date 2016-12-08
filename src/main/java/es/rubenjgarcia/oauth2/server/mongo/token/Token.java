package es.rubenjgarcia.oauth2.server.mongo.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Document
@CompoundIndex(def = "{client: 1, user: 1}")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Token implements OAuth2AccessToken {

    @Id
    private String accessToken;

    @Indexed
    private String client;

    private String user;

    @Field("expires_in")
    private Date expiration;

    @Field("refresh_token")
    private String refreshToken;

    @Field("refresh_token_expiration")
    @JsonIgnore
    private Date refreshTokenExpiration;

    @Field("token_type")
    private String tokenType;

    @Field("additional_information")
    private Map<String, Object> additionalInformation = new LinkedHashMap<>();

    @JsonIgnore
    private String authentication;

    private Set<String> scope = Collections.emptySet();

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return this.additionalInformation;
    }

    @Override
    public Set<String> getScope() {
        return this.scope;
    }

    @Override
    public OAuth2RefreshToken getRefreshToken() {
        return new DefaultOAuth2RefreshToken(this.refreshToken);
    }

    @Override
    public String getTokenType() {
        return this.tokenType;
    }

    @Override
    public boolean isExpired() {
        return Instant.now().isAfter(this.expiration.toInstant());
    }

    @Override
    public Date getExpiration() {
        return this.expiration;
    }

    @Override
    public int getExpiresIn() {
        return (int) ChronoUnit.MILLIS.between(Instant.now(), this.expiration.toInstant());
    }

    @Override
    public String getValue() {
        return this.accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    public void setRefreshTokenExpiration(Date refreshTokenExpiration) {
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getAuthentication() {
        return authentication;
    }
}
