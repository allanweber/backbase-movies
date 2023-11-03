package com.backbase.movies.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)//
class AuthenticationServiceTest {

    AuthenticationService authenticationService = new AuthenticationService();

    @DisplayName("Given server request with token return Authentication object typeof ApiKeyAuthentication")
    @Test
    void withToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("X-API-KEY")).thenReturn("3307cf63");

        Optional<Authentication> authentication = authenticationService.getAuthentication(request);

        assertTrue(authentication.isPresent());
        assertEquals(authentication.get().getClass(), AuthenticationService.ApiKeyAuthentication.class);
        assertEquals(authentication.get().getPrincipal(), "3307cf63");
    }

    @DisplayName("Given server request without token return empty Authentication object")
    @Test
    void withoutToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("X-API-KEY")).thenReturn(null);

        Optional<Authentication> authentication = authenticationService.getAuthentication(request);

        assertFalse(authentication.isPresent());
    }

}