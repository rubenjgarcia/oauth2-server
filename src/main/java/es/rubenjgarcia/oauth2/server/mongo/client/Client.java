package es.rubenjgarcia.oauth2.server.mongo.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;
import java.util.stream.Collectors;

@Document
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Client implements ClientDetails {

    @Id
    @JsonProperty("client_id")
    private String clientId;

    @Field("client_secret")
    @JsonProperty("client_secret")
    private String clientSecret;

    private List<String> authorities = Collections.emptyList();
    private Set<String> scopes = Collections.emptySet();

    @Field("resource_ids")
    @JsonProperty("resource_ids")
    private Set<String> resourceIds = Collections.emptySet();

    @Field("authorized_grant_types")
    @JsonProperty("authorized_grant_types")
    private Set<String> authorizedGrantTypes = Collections.emptySet();

    @Field("auto_approve_scopes")
    @JsonProperty("auto_approve_scopes")
    private Set<String> autoApproveScopes;

    @Field("registered_redirect_uris")
    @JsonProperty("registered_redirect_uris")
    private Set<String> registeredRedirectUris;

    @Field("access_token_validity_seconds")
    @JsonProperty("access_token_validity_seconds")
    private Integer accessTokenValiditySeconds;

    @Field("refresh_token_validity_seconds")
    @JsonProperty("refresh_token_validity_seconds")
    private Integer refreshTokenValiditySeconds;

    @Field("additional_information")
    @JsonProperty("additional_information")
    private Map<String, Object> additionalInformation = new LinkedHashMap<>();

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return this.resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public boolean isScoped() {
        return this.scopes != null && !this.scopes.isEmpty();
    }

    @Override
    public Set<String> getScope() {
        return this.scopes;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return this.authorizedGrantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return this.registeredRedirectUris;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(this.authorities)
                .orElse(Collections.emptyList())
                .stream().map(a -> (GrantedAuthority) () -> a)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return this.accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return this.refreshTokenValiditySeconds;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        if (this.autoApproveScopes == null) {
            return false;
        }

        return this.autoApproveScopes.stream()
                .anyMatch(a -> a.equals("true") || scope.matches(a));
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return Collections.unmodifiableMap(this.additionalInformation);
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }

    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public Set<String> getAutoApproveScopes() {
        return autoApproveScopes;
    }

    public void setAutoApproveScopes(Set<String> autoApproveScopes) {
        this.autoApproveScopes = autoApproveScopes;
    }

    public Set<String> getRegisteredRedirectUris() {
        return registeredRedirectUris;
    }

    public void setRegisteredRedirectUris(Set<String> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public void setAutoApproveScopes(Collection<String> autoApproveScopes) {
        this.autoApproveScopes = new HashSet<>(autoApproveScopes);
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
