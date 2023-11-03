package com.backbase.movies.api;

import com.backbase.movies.configuration.AuthenticationService;
import com.backbase.movies.domain.movies.repository.Category;
import com.backbase.movies.domain.movies.repository.Movie;
import com.backbase.movies.domain.movies.repository.MovieRepository;
import com.backbase.movies.domain.movies.repository.Nominee;
import com.backbase.movies.test_tools.MongoDbTestContainerInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = MongoDbTestContainerInitializer.class)
class BestPictureControllerTest {

    @MockBean
    AuthenticationService authenticationService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        when(authenticationService.getAuthentication(any()))
                .thenReturn(of(new AuthenticationService.ApiKeyAuthentication("aaa", AuthorityUtils.NO_AUTHORITIES)));
    }

    @AfterEach
    void tearDown() {
        movieRepository.deleteAll();
    }

    @DisplayName("Look for a movie that won Best Picture nomination return true")
    @Test
    void bestPicture() {
        Movie movie = new Movie("The Godfather", 1972, singletonList(new Nominee(Category.BEST_PICTURE, "Best Picture", true)));
        Movie movie2 = new Movie("The Godfather II", 1980, singletonList(new Nominee(Category.BEST_PICTURE, "Best Picture", false)));
        movieRepository.save(movie);
        movieRepository.save(movie2);

        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/movies/best-picture/won")
                        .queryParam("title", "The Godfather")
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.won").isEqualTo(true);
    }

    @DisplayName("Look for a movie that did not win Best Picture nomination return false")
    @Test
    void notBestPicture() {
        Movie movie = new Movie("The Godfather", 1972, singletonList(new Nominee(Category.BEST_PICTURE, "Best Picture", true)));
        Movie movie2 = new Movie("Rambo", 1980, singletonList(new Nominee(Category.BEST_PICTURE, "Best Picture", false)));
        movieRepository.save(movie);
        movieRepository.save(movie2);

        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/movies/best-picture/won")
                        .queryParam("title", "Rambo")
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.won").isEqualTo(false);
    }

    @DisplayName("Look for a movie that does not exist in database return false for best picture")
    @Test
    void notFound() {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/movies/best-picture/won")
                        .queryParam("title", "random movie")
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.won").isEqualTo(false);
    }
}