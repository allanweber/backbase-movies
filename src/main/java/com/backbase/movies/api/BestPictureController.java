package com.backbase.movies.api;

import com.backbase.movies.domain.movies.BestPictureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RequestMapping("/v1/movies/best-picture")
@RestController
public class BestPictureController {

    private final BestPictureService bestPictureService;

    public BestPictureController(BestPictureService bestPictureService) {
        this.bestPictureService = bestPictureService;
    }

    @GetMapping("/won")
    ResponseEntity<WonResponse> wonByTitle(@RequestParam(value = "title") String title) {
        boolean bestPicture = bestPictureService.wonBestPicture(title);
        return ok(new WonResponse(bestPicture));
    }

    private record WonResponse(boolean won) {
    }
}
