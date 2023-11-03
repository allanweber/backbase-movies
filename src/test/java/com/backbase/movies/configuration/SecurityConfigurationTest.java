package com.backbase.movies.configuration;

import com.backbase.movies.MongoDbTestContainerInitializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = MongoDbTestContainerInitializer.class)
class SecurityConfigurationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Access protected path anonymously fails")
    void anonymouslyFails() {
        webTestClient
                .get()
                .uri("/v1/movies/ratings/top-rated?limit=10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    @DisplayName("Access public path anonymously succeeds")
    void publicIsFine() {
        webTestClient
                .get()
                .uri("/health")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("Access protected path with invalid token fails")
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

    @Test
    @DisplayName("Access protected path with valid token succeeds")
    void validToken() {
        webTestClient
                .get()
                .uri("/v1/movies/ratings/top-rated?limit=10")
                .header("X-API-KEY", "a0c16fd2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }
}