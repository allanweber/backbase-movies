package com.backbase.movies.configuration;

import com.backbase.movies.test_tools.MongoDbTestContainerInitializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = MongoDbTestContainerInitializer.class)
class SecurityConfigurationTest {

    @MockBean
    AuthenticationService authenticationService;

    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("Access protected path anonymously fails")
    @ParameterizedTest
    @ValueSource(strings = {"/v1/movies/ratings/top-rated?limit=10", "/v1/movies/best-picture/won?title=The Godfather"})
    void anonymouslyFails(String path) {
        webTestClient
                .get()
                .uri(path)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @DisplayName("Put a rating anonymously fails")
    @Test
    void rate() {
        webTestClient
                .put()
                .uri(builder -> builder
                        .path("/v1/movies/ratings")
                        .queryParam("title", "The Godfather")
                        .queryParam("rating", 5)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @DisplayName("Access public path anonymously succeeds")
    @Test
    void publicIsFine() {
        webTestClient
                .get()
                .uri("/health")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @DisplayName("Access protected path with invalid token fails")
    @Test
    void invalidToken() {
        webTestClient
                .get()
                .uri("/v1/movies/ratings/top-rated?limit=10")
                .header("X-API-KEY", "invalid")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @DisplayName("Access protected path with valid token succeeds")
    @Test
    void validToken() {
        when(authenticationService.getAuthentication(any())).thenReturn(of(new AuthenticationService.ApiKeyAuthentication("aaa", AuthorityUtils.NO_AUTHORITIES)));

        webTestClient
                .get()
                .uri("/v1/movies/ratings/top-rated?limit=10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }
}