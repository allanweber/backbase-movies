package com.backbase.movies.domain.movieapi.impl;

import com.backbase.movies.domain.movieapi.MovieCollectionApi;
import com.backbase.movies.domain.movieapi.MovieEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static java.util.Optional.of;

@Service
public class OmdbMovieApi implements MovieCollectionApi {

    private final Logger log = LoggerFactory.getLogger(OmdbMovieApi.class);

    private final RestTemplate restTemplate;

    public OmdbMovieApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<MovieEntry> searchMovie(String movieTitle) {
        log.info("Searching movie {} in OMDB", movieTitle);
        String encodeTitle = URLEncoder.encode(movieTitle, StandardCharsets.UTF_8);
        String apiKey = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String url = UriComponentsBuilder.fromHttpUrl("http://www.omdbapi.com")
                .queryParam("apikey", apiKey)
                .queryParam("t", encodeTitle)
                .build(false).toUriString();

        ResponseEntity<MovieEntry> response = restTemplate.getForEntity(url, MovieEntry.class);
        log.info("Response from OMDB: {}", response);

        Optional<MovieEntry> movie = Optional.empty();
        if (response.getBody() != null && response.getBody().isResponse()) {
            movie = of(response.getBody());
        }
        return movie;
    }
}
