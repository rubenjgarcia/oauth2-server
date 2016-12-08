package es.rubenjgarcia.oauth2.server.mongo.user;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.config.annotation.authentication.ProviderManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.crypto.password.AbstractPasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MongoUserDetailsManagerConfigurer<B extends ProviderManagerBuilder<B>> extends UserDetailsManagerConfigurer<B, MongoUserDetailsManagerConfigurer<B>> {

    public MongoUserDetailsManagerConfigurer(MongoUserDetailsManager mongoUserDetailsManager) {
        super(mongoUserDetailsManager);

        this.passwordEncoder(new AbstractPasswordEncoder() {

            @Override
            protected byte[] encode(CharSequence rawPassword, byte[] salt) {
                try {
                    MessageDigest digest = MessageDigest.getInstance("SHA-512");
                    digest.reset();
                    Base64 decoder = new Base64();
                    digest.update(decoder.decode(salt));
                    byte[] input = digest.digest(String.valueOf(rawPassword).getBytes());
                    digest.reset();
                    return digest.digest(input);
                } catch (NoSuchAlgorithmException e) {
                    throw new IllegalArgumentException("Provider does not support SHA-512");
                }
            }
        });
    }
}
