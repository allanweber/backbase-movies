package com.backbase.movies.domain.movieapi.impl;

import com.backbase.movies.domain.movieapi.MovieCollectionApi;
import com.backbase.movies.domain.movieapi.MovieEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Service
public class OmdbMovieApi implements MovieCollectionApi {

    private final RestTemplate restTemplate;

    public OmdbMovieApi() {
        this.restTemplate = new RestTemplate();
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        this.restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);
    }

    @Override
    public Optional<MovieEntry> searchMovie(String movieTitle) {
        String encodeTitle = URLEncoder.encode(movieTitle, StandardCharsets.UTF_8);
        String apiKey = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String url = UriComponentsBuilder.fromHttpUrl("http://www.omdbapi.com")
                .queryParam("apikey", apiKey)
                .queryParam("t", encodeTitle)
                .build(false).toUriString();

        ResponseEntity<MovieEntry> response = restTemplate.getForEntity(url, MovieEntry.class);

        if (response.getBody() != null && response.getBody().isResponse()) {
            return of(response.getBody());
        }

        return empty();
    }
}
