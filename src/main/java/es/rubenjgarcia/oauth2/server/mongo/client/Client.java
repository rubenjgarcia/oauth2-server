package es.rubenjgarcia.oauth2.server.mongo.client;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Map;
import java.util.Set;

@Document
public class Client extends BaseClientDetails {

    public Client() {
        super();
    }

    public Client(ClientDetails prototype) {
        super(prototype);
    }

    @Id
    @Override
    public String getClientId() {
        return super.getClientId();
    }

    @Field("client_secret")
    @Override
    public String getClientSecret() {
        return super.getClientSecret();
    }

    @Field("resource_ids")
    @Override
    public Set<String> getResourceIds() {
        return super.getResourceIds();
    }

    @Field("authorized_grant_types")
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return super.getAuthorizedGrantTypes();
    }

    @Field("auto_approve_scopes")
    @Override
    public Set<String> getAutoApproveScopes() {
        return super.getAutoApproveScopes();
    }

    @Field("registered_redirect_uris")
    @Override
    public Set<String> getRegisteredRedirectUri() {
        return super.getRegisteredRedirectUri();
    }

    @Field("access_token_validity_seconds")
    @Override
    public Integer getAccessTokenValiditySeconds() {
        return super.getAccessTokenValiditySeconds();
    }

    @Field("refresh_token_validity_seconds")
    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return super.getRefreshTokenValiditySeconds();
    }

    @Field("additional_information")
    @Override
    public Map<String, Object> getAdditionalInformation() {
        return super.getAdditionalInformation();
    }
}
