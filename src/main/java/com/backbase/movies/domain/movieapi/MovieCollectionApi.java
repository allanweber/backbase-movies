package com.backbase.movies.domain.movieapi;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MovieCollectionApi {

    Optional<MovieEntry> searchMovie(String movieTitle);
}
