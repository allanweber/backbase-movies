package com.backbase.movies.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;

@Service
public class AuthenticationService {
    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

    // TODO: Mock valid tokens, since the token generation is not implemented yet
    private static final List<String> VALID_TOKENS = List.of("3307cf63", "a0c16fd2");

    public Optional<Authentication> getAuthentication(HttpServletRequest request) {
        Optional<Authentication> authentication = Optional.empty();
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (apiKey != null && VALID_TOKENS.contains(apiKey)) {
            authentication = of(new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES));
        }
        return authentication;
    }

    public static class ApiKeyAuthentication extends AbstractAuthenticationToken {
        private final String apiKey;

        public ApiKeyAuthentication(String apiKey, Collection<? extends GrantedAuthority> authorities) {
            super(authorities);
            this.apiKey = apiKey;
            setAuthenticated(true);
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return apiKey;
        }
    }
}


