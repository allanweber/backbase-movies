package com.backbase.movies.seed;

import com.backbase.movies.domain.movies.repository.Category;
import com.backbase.movies.domain.movies.repository.Movie;
import com.backbase.movies.domain.movies.MovieService;
import com.backbase.movies.domain.movies.repository.Nominee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BestPictureSeed {


    private final MovieService movieService;

    public BestPictureSeed(MovieService movieService) {
        this.movieService = movieService;
    }

    BestPictureParser parser;

    public void seed(List<SeedRecord> records) {
        for (SeedRecord record : records) {
            parser = new BestPictureParser(record);

            int year = parser.getYear();
            String movieTitle = parser.getMovieTitle();
            Movie movie = movieService.getOrCreateMovie(new Movie(movieTitle, year));
            movieService.addNominee(movie.getId(), new Nominee(Category.BEST_PICTURE, parser.getAdditionalInfo(), parser.won()));
        }
    }


}
