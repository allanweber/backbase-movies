package com.backbase.movies.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Optional;

public class AuthenticationFilter extends GenericFilterBean {

    private final AuthenticationService authenticationService;

    public AuthenticationFilter(AuthenticationService authenticationService) {
        super();
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        Optional<Authentication> authentication = authenticationService.getAuthentication((HttpServletRequest) request);
        if (authentication.isPresent()) {
            SecurityContextHolder.getContext().setAuthentication(authentication.get());
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }


}
