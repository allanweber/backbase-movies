package com.backbase.movies.domain;

import com.backbase.movies.domain.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie getOrCreateMovie(Movie movie) {
        return movieRepository.getByTitleAndYear(movie.getTitle(), movie.getYear())
                .orElseGet(() -> movieRepository.save(movie));
    }

    public void addNominee(String movieId, Nominee nominee) {
        movieRepository.addNominee(movieId, nominee);
    }
}
