package com.backbase.movies.seed;

import com.backbase.movies.domain.movies.MovieService;
import com.backbase.movies.domain.movies.repository.Category;
import com.backbase.movies.domain.movies.repository.Movie;
import com.backbase.movies.domain.movies.repository.Nominee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BestPictureSeed {

    private final Logger log = LoggerFactory.getLogger(BestPictureSeed.class);

    private final MovieService movieService;

    public BestPictureSeed(MovieService movieService) {
        this.movieService = movieService;
    }


    public void seed(List<SeedRecord> records) {
        BestPictureParser parser;
        for (SeedRecord record : records) {
            parser = new BestPictureParser(record);
            int year = parser.getYear();
            String movieTitle = parser.getMovieTitle();
            log.info("Processing movie {} - {}", movieTitle, year);
            Movie movie = movieService.getOrCreateMovie(new Movie(movieTitle, year));
            log.info("Nominated movie {} - {}", parser.getAdditionalInfo(), parser.won());
            movieService.addNominee(movie.getId(), new Nominee(Category.BEST_PICTURE, parser.getAdditionalInfo(), parser.won()));
        }
    }


}
