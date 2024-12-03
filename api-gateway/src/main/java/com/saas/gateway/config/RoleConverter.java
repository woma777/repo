package com.saas.gateway.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
public class RoleConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";

    @Override
    public Mono<AbstractAuthenticationToken> convert(@NonNull Jwt jwt) {
        return Mono.just(new JwtAuthenticationToken(jwt, extractAuthorities(jwt)));
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {

        Map<String, Object> realmAccess = jwt.getClaim(REALM_ACCESS_CLAIM);
        if (realmAccess != null && !realmAccess.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            List<String> keycloakRoles = mapper.convertValue(realmAccess.get(ROLES_CLAIM),
                    new TypeReference<List<String>>() {});
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (String keycloakRole : keycloakRoles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(keycloakRole));
            }
            return grantedAuthorities;
        }
        return new ArrayList<>();
    }
}
