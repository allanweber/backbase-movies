package com.backbase.movies.domain.movies;

import com.backbase.movies.domain.imdb.MovieCollectionApi;
import com.backbase.movies.domain.movies.repository.Movie;
import com.backbase.movies.domain.movies.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class RatingService {

    private final MovieRepository movieRepository;

    private final MovieCollectionApi movieCollectionApi;

    public RatingService(MovieRepository movieRepository, MovieCollectionApi movieCollectionApi) {
        this.movieRepository = movieRepository;
        this.movieCollectionApi = movieCollectionApi;
    }

    public Movie rate(String movieTitle, double rate) {
        Movie movie = movieRepository.getByTitle(movieTitle)
                .orElseGet(() -> getFromApi(movieTitle));

        movie.getRate().rate(rate);

        return movieRepository.save(movie);
    }

    private Movie getFromApi(String movieTitle) {
        return movieCollectionApi.searchMovie(movieTitle)
                .map(entry -> new Movie(entry.title(), entry.year()))
                .map(movieRepository::save)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format("Movie %s not found", movieTitle)));
    }
}
