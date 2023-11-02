package com.backbase.movies.domain.movies;

import com.backbase.movies.domain.movieapi.MovieCollectionApi;
import com.backbase.movies.domain.movieapi.MovieEntry;
import com.backbase.movies.domain.movies.repository.Movie;
import com.backbase.movies.domain.movies.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

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
                .orElseGet(() -> newMovieFromCollection(movieTitle));

        if (movie.getBoxOffice() == 0) {
            double boxOffice = retrieveBoxOffice(movieTitle);
            movie.setBoxOffice(boxOffice);
        }

        movie.getRate().rate(rate);
        return movieRepository.save(movie);
    }

    public List<Movie> topRated(int limit) {
        List<Movie> movies = movieRepository.topRated(limit);
//        movies.sort((m1, m2) -> Double.compare(m2.getBoxOffice(), m1.getBoxOffice()));
        return movies;
    }

    private double retrieveBoxOffice(String movieTitle) {
        return searchInMovieCollection(movieTitle)
                .map(MovieEntry::getBoxOffice)
                .orElse(0.0);
    }

    private Movie newMovieFromCollection(String movieTitle) {
        return searchInMovieCollection(movieTitle)
                .map(entry -> new Movie(entry.getTitle(), entry.getYear(), entry.getBoxOffice()))
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format("Movie %s not found", movieTitle)));
    }

    private Optional<MovieEntry> searchInMovieCollection(String movieTitle) {
        return movieCollectionApi.searchMovie(movieTitle);
    }
}
