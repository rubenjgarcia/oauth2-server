package es.rubenjgarcia.oauth2.server.mongo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class StringToGrantedAuthority implements Converter<String, GrantedAuthority> {

    @Override
    public GrantedAuthority convert(String source) {
        return new SimpleGrantedAuthority(source);
    }
}
