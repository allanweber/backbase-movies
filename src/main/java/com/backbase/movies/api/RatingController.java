package com.backbase.movies.api;

import com.backbase.movies.domain.movies.Helper;
import com.backbase.movies.domain.movies.RatingService;
import com.backbase.movies.domain.movies.repository.Movie;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<RateResponse> rate(@RequestParam(value = "title") String title, @RequestParam(value = "rate") @Valid @Positive double rate) {
        rate = Helper.doublePrecision(rate);
        Movie movie = ratingService.rate(title, rate);
        RateResponse response = new RateResponse(movie.getTitle(), movie.getRate().getCurrentRate(), movie.getRate().getRatings().size());
        return ok(response);
    }

    @GetMapping("/top-rated")
    ResponseEntity<List<TopRatedResponse>> topRated(@RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        List<Movie> movies = ratingService.topRated(limit);
        List<TopRatedResponse> top = movies.stream().map(movie -> new TopRatedResponse(movie.getTitle(), movie.getYear(), movie.getRate().getCurrentRate(), movie.getBoxOffice())).toList();
        return ok(top);
    }

    private record RateResponse(String movie, double currentRate, int ratings) {
    }

    public record TopRatedResponse(String movie, int year, double rate, double boxOffice) {
    }
}
