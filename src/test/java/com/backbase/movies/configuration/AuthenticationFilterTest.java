package com.backbase.movies.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationFilterTest {

    @Mock
    AuthenticationService authenticationService;

    AuthenticationFilter authenticationFilter;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.getContext().setAuthentication(null);
        authenticationFilter = new AuthenticationFilter(authenticationService);
    }


    @DisplayName("Given server request with token process request successfully and put authentication in context")
    @Test
    void withToken() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        MockFilterChain chain = new MockFilterChain();
        when(authenticationService.getAuthentication(any())).thenReturn(of(new AuthenticationService.ApiKeyAuthentication("API_KEY", AuthorityUtils.NO_AUTHORITIES)));

        authenticationFilter.doFilter(request, response, chain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertEquals(authentication.getPrincipal(), "API_KEY");
    }

    @DisplayName("Given server request without token process request successfully but authentication is null")
    @Test
    void noToken() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        MockFilterChain chain = new MockFilterChain();
        when(authenticationService.getAuthentication(any())).thenReturn(empty());

        authenticationFilter.doFilter(request, response, chain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
    }
}