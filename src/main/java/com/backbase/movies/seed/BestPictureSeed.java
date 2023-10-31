package com.backbase.movies.seed;

import com.backbase.movies.model.Category;
import com.backbase.movies.model.Movie;
import com.backbase.movies.model.MovieService;
import com.backbase.movies.model.Nominee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BestPictureSeed extends SeedParser {

    private static final List<String> NOT_A_MOVIE = List.of("Feature Productions", "Fox", "Metro-Goldwyn-Mayer", "Paramount Famous Lasky", "The Caddo Company");

    private final MovieService movieService;

    public BestPictureSeed(MovieService movieService) {
        this.movieService = movieService;
    }

    public void seed(List<SeedRecord> records) {
        for (SeedRecord record : records) {
            parse(record);
            int year = getYear();
            String movieTitle = getMovieTitle();
            Movie movie = movieService.getOrCreateMovie(new Movie(movieTitle, year));
            movieService.setNominee(movie.getId(), new Nominee(Category.BEST_PICTURE, getAdditionalInfo(), won()));
        }
    }

    @Override
    protected String getMovieTitle() {
        String movieTitle = super.getMovieTitle();
        if (NOT_A_MOVIE.contains(movieTitle)) {
            movieTitle = getAdditionalInfo();
        }
        return movieTitle;
    }

}
