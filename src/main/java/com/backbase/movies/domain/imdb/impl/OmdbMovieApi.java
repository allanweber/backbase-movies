package com.backbase.movies.domain.imdb.impl;

import com.backbase.movies.domain.imdb.MovieCollectionApi;
import com.backbase.movies.domain.imdb.MovieEntry;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OmdbMovieApi implements MovieCollectionApi {
    @Override
    public Optional<MovieEntry> searchMovie(String movieTitle) {
        return Optional.empty();
    }
}
