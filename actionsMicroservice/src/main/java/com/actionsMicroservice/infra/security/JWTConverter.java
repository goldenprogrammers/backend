package com.actionsMicroservice.infra.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
public class JWTConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Value("${KEYCLOAK_CLIENT}")
    private String keycloakClient;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Map<String, Collection<String>> resourceAccess = jwt.getClaim("resource_access");
        Map<String, Object> client = (Map<String, Object>) resourceAccess.get(keycloakClient);
        Collection<String> roles = (Collection<String>) client.get("roles");
        var grants = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList();

        return new JwtAuthenticationToken(jwt, grants);
    }
}
