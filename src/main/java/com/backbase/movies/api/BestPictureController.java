package com.backbase.movies.api;

import com.backbase.movies.domain.movies.BestPictureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RequestMapping("/v1/movies/best-picture")
@RestController
public class BestPictureController {

    private final BestPictureService bestPictureService;

    public BestPictureController(BestPictureService bestPictureService) {
        this.bestPictureService = bestPictureService;
    }

    @GetMapping("/won/{movie-id}")
    ResponseEntity<WonResponse> won(@PathVariable(name = "movie-id") String movieId) {
        boolean bestPicture = bestPictureService.wonBestPicture(movieId);
        return ok(new WonResponse(bestPicture));
    }

    @GetMapping("/won")
    ResponseEntity<WonResponse> wonByTitle(@RequestParam(value = "title") String title) {
        boolean bestPicture = bestPictureService.wonBestPictureByMovieTitle(title);
        return ok(new WonResponse(bestPicture));
    }

    private record WonResponse(boolean won) {
    }
}
