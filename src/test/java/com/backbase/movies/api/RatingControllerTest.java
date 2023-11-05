package com.backbase.movies.api;

import com.backbase.movies.configuration.AuthenticationService;
import com.backbase.movies.domain.movies.repository.Movie;
import com.backbase.movies.domain.movies.repository.MovieRepository;
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

import java.math.BigDecimal;

import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = MongoDbTestContainerInitializer.class)
class RatingControllerTest {

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

    @DisplayName("Get top rated on an empty database return empty response")
    @Test
    void topRatedEmpty() {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/movies/ratings")
                        .path("/top-rated")
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(0);
    }

    @DisplayName("After rate 5 movies, get top 3 rated movies return 5 movies sorted by rate and box office")
    @Test
    void topRated() {
        Movie movie = new Movie("The Godfather", 2020, BigDecimal.valueOf(100));
        movie.rateMovie(10);
        movieRepository.save(movie);

        movie = new Movie("Rambo", 2020, BigDecimal.valueOf(200));
        movie.rateMovie(2);
        movieRepository.save(movie);

        movie = new Movie("Avatar", 2020, BigDecimal.valueOf(300));
        movie.rateMovie(1);
        movieRepository.save(movie);

        movie = new Movie("Matrix", 2020, BigDecimal.valueOf(150));
        movie.rateMovie(8);
        movieRepository.save(movie);

        movie = new Movie("It", 2020, BigDecimal.valueOf(250));
        movie.rateMovie(7);
        movieRepository.save(movie);

        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/movies/ratings")
                        .path("/top-rated")
                        .queryParam("limit", 3)
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$[0].movie").isEqualTo("It")
                .jsonPath("$[1].movie").isEqualTo("Matrix")
                .jsonPath("$[2].movie").isEqualTo("The Godfather");
    }

    @DisplayName("Rate a movie for the first time with a valid rate return the movie with the new rate")
    @Test
    void rateFirst() {
        Movie movie = new Movie("The Godfather", 2020, BigDecimal.valueOf(100));
        movieRepository.save(movie);

        webTestClient
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/movies/ratings")
                        .path("/rate")
                        .queryParam("title", "The Godfather")
                        .queryParam("rate", 10)
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.movie").isEqualTo("The Godfather")
                .jsonPath("$.currentRate").isEqualTo(10)
                .jsonPath("$.ratings").isEqualTo(1);
    }

    @DisplayName("Rate a movie already rated return the avarage rate")
    @Test
    void averageRate() {
        Movie movie = new Movie("The Godfather", 2020, BigDecimal.valueOf(100));
        movie.rateMovie(10);
        movie.rateMovie(9);
        movie.rateMovie(8);
        movieRepository.save(movie);

        webTestClient
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/movies/ratings")
                        .path("/rate")
                        .queryParam("title", "The Godfather")
                        .queryParam("rate", 7)
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.movie").isEqualTo("The Godfather")
                .jsonPath("$.currentRate").isEqualTo(8.5)
                .jsonPath("$.ratings").isEqualTo(4);
    }

    @DisplayName("Rate a movie with a rate greater than 10 return bad request")
    @Test
    void rateGreaterThan10() {
        webTestClient
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/movies/ratings")
                        .path("/rate")
                        .queryParam("title", "The Godfather")
                        .queryParam("rate", 11)
                        .build())
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .jsonPath("$.error").isEqualTo("rate.rate: must be less than or equal to 10");
    }

    @DisplayName("Rate a movie with a rate less than 0 return bad request")
    @Test
    void rateLessThan0() {
        webTestClient
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/movies/ratings")
                        .path("/rate")
                        .queryParam("title", "The Godfather")
                        .queryParam("rate", -1)
                        .build())
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .jsonPath("$.error").isEqualTo("rate.rate: must be greater than 0");
    }
}