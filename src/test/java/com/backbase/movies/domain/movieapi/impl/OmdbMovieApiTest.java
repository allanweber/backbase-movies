package com.backbase.movies.domain.movieapi.impl;

import com.backbase.movies.configuration.AuthenticationService;
import com.backbase.movies.domain.movieapi.MovieEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OmdbMovieApiTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    OmdbMovieApi omdbMovieApi;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(
                new AuthenticationService.ApiKeyAuthentication("1234567890", AuthorityUtils.NO_AUTHORITIES));
    }

    @DisplayName("Given a movie title, when searching for a movie, then a movie entry is returned")
    @Test
    void searchMovie() {
        String movieTitle = "Rambo";

        when(restTemplate.getForEntity(
                "http://www.omdbapi.com?apikey=1234567890&t=Rambo",
                MovieEntry.class
        )).thenReturn(ResponseEntity.ok(
                new MovieEntry(movieTitle, 1999, new BigDecimal("1000.0"), true)
        ));

        MovieEntry movieEntry = omdbMovieApi.searchMovie(movieTitle).orElseThrow();

        assertEquals(movieTitle, movieEntry.getTitle());
    }

    @DisplayName("Given a movie title, with spacial characters, when searching for a movie, encode it")
    @Test
    void searchMovieEncoded() {
        String movieTitle = "The' something: encoded, this";

        when(restTemplate.getForEntity(
                "http://www.omdbapi.com?apikey=1234567890&t=The%27+something%3A+encoded%2C+this",
                MovieEntry.class
        )).thenReturn(ResponseEntity.ok(
                new MovieEntry(movieTitle, 1999, new BigDecimal("1000.0"), true)
        ));

        MovieEntry movieEntry = omdbMovieApi.searchMovie(movieTitle).orElseThrow();

        assertEquals(movieTitle, movieEntry.getTitle());
    }

    @DisplayName("Given a movie title, when searching for a movie, no movie not found, then an empty optional is returned")
    @ParameterizedTest
    @MethodSource("failResponses")
    void notFound(ResponseEntity<MovieEntry> response) {
        String movieTitle = "The Matrix";

        when(restTemplate.getForEntity(
                "http://www.omdbapi.com?apikey=1234567890&t=The+Matrix",
                MovieEntry.class
        )).thenReturn(response);

        Optional<MovieEntry> movieEntry = omdbMovieApi.searchMovie(movieTitle);

        assertTrue(movieEntry.isEmpty());
    }

    private static Stream<ResponseEntity<MovieEntry>> failResponses() {
        return Stream.of(
                ResponseEntity.ok().body(null),
                ResponseEntity.ok(new MovieEntry(null, 0, null, false)),
                ResponseEntity.status(400).build()
        );
    }
}