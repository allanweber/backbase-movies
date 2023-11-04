package com.backbase.movies.api;

import com.backbase.movies.domain.movies.BestPictureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class BestPictureController implements BestPictureApi {

    private final BestPictureService bestPictureService;

    public BestPictureController(BestPictureService bestPictureService) {
        this.bestPictureService = bestPictureService;
    }

    @Override
    public ResponseEntity<WonResponse> wonByTitle(String title) {
        boolean bestPicture = bestPictureService.wonBestPicture(title);
        return ok(new WonResponse(bestPicture));
    }

    public record WonResponse(boolean won) {
    }
}
