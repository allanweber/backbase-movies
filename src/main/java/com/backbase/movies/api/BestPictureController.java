package com.backbase.movies.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RequestMapping("/v1/movies/best-picture")
@RestController
public class BestPictureController {

    @GetMapping("/won/{movie-id}")
    ResponseEntity<WonResponse> won(@PathVariable(name = "movie-id") String movieId) {
        return ok(new WonResponse(true));
    }

    @GetMapping("/won")
    ResponseEntity<WonResponse> wonByTitle(@RequestParam(value = "title", required = false) String title) {
        return ok(new WonResponse(true));
    }

    public record WonResponse(boolean won) {};
}
