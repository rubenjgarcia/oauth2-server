package es.rubenjgarcia.oauth2.server.mongo.token;

import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MongoTokenStore implements TokenStore {

    private final TokenRepository tokenRepository;

    public MongoTokenStore(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        Token tokenDb = this.tokenRepository.findOne(token);

        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] serializedAuthentication = decoder.decodeBuffer(tokenDb.getAuthentication());
            return SerializationUtils.deserialize(serializedAuthentication);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error deserializing OAuth2Authentication");
        }
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Token tokenDb;
        if (Token.class.isAssignableFrom(token.getClass())) {
            tokenDb = (Token) token;
        } else {
            tokenDb = new Token();
        }

        tokenDb.setExpiration(token.getExpiration());
        tokenDb.setAccessToken(token.getValue());
        tokenDb.setUser(authentication.getName());
        tokenDb.setClient(authentication.getOAuth2Request().getClientId());
        tokenDb.setScope(token.getScope());
        tokenDb.setTokenType(token.getTokenType());
        tokenDb.setAdditionalInformation(token.getAdditionalInformation());

        byte[] serializedAuthentication = SerializationUtils.serialize(authentication);
        BASE64Encoder encoder = new BASE64Encoder();
        String encodedAuthentication = encoder.encode(serializedAuthentication);
        tokenDb.setAuthentication(encodedAuthentication);
        this.tokenRepository.save(tokenDb);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        return this.tokenRepository.findOne(tokenValue);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        tokenRepository.delete(token.getValue());
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        List<Token> byClientAndUser = this.tokenRepository.findByClientAndUser(authentication.getOAuth2Request().getClientId(), authentication.getName());
        assert(byClientAndUser.size() == 1); //FIXME

        Token token = byClientAndUser.get(0);
        token.setRefreshToken(refreshToken.getValue());

        if (ExpiringOAuth2RefreshToken.class.isAssignableFrom(refreshToken.getClass())) {
            token.setRefreshTokenExpiration(((ExpiringOAuth2RefreshToken) refreshToken).getExpiration());
        }

        this.tokenRepository.save(token);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        return this.tokenRepository.findByClientAndExpirationAfter(authentication.getOAuth2Request().getClientId(), new Date());
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        return new ArrayList<>(this.tokenRepository.findByClientAndUser(clientId, userName));
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        return new ArrayList<>(this.tokenRepository.findByClient(clientId));
    }
}
