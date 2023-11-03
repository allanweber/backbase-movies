package com.backbase.movies.api;

import com.backbase.movies.domain.movies.DoubleHelper;
import com.backbase.movies.domain.movies.RatingService;
import com.backbase.movies.domain.movies.repository.Movie;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RequestMapping("/v1/movies/ratings")
@RestController
@Validated
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PutMapping("/rate")
    public ResponseEntity<RateResponse> rate(@RequestParam String title, @RequestParam @Valid @Positive @Max(10) double rate) {
        double correctRate = DoubleHelper.precision(rate);
        Movie movie = ratingService.rate(title, correctRate);
        RateResponse response = new RateResponse(movie.getTitle(), movie.getRate().getCurrentRate(), movie.getRate().getRatings().size());
        return ok(response);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<TopRatedResponse>> topRated(@RequestParam(required = false, defaultValue = "10") int limit) {
        List<Movie> movies = ratingService.topRated(limit);
        List<TopRatedResponse> top = movies.stream().map(movie -> new TopRatedResponse(movie.getTitle(), movie.getYear(), movie.getRate().getCurrentRate(), movie.getBoxOffice())).toList();
        return ok(top);
    }

    public record RateResponse(String movie, double currentRate, int ratings) {
    }

    public record TopRatedResponse(String movie, int year, double rate,
                                   @NumberFormat(pattern = "#0.00") BigDecimal boxOffice) {
    }
}
