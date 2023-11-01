package com.backbase.movies.domain.imdb;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MovieCollectionApi {

    Optional<MovieEntry> searchMovie(String movieTitle);
}
