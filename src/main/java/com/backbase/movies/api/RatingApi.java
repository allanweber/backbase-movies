package com.backbase.movies.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/v1/movies/ratings")
@Tag(name = "Ratings", description = "Ratings API")
public interface RatingApi {

    @Operation(
            summary = "Rate a movie",
            description = "Returns the movie title, current rate and number of ratings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = RatingController.RateResponse.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
    })
    @PutMapping("/rate")
    ResponseEntity<RatingController.RateResponse> rate(@RequestParam String title, @RequestParam @Valid @Positive @Max(10) double rate);

    @Operation(
            summary = "Top rated movies",
            description = "Returns the top rated movies sorted by box office descending")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = RatingController.RateResponse.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
    })
    @GetMapping("/top-rated")
    ResponseEntity<List<RatingController.TopRatedResponse>> topRated(@RequestParam(required = false, defaultValue = "10") int limit);
}
