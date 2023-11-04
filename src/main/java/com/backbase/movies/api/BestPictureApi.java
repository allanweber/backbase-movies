package com.backbase.movies.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/movies/best-picture")
@Tag(name = "Best Picture", description = "Best Picture API")
public interface BestPictureApi {

    @Operation(
            summary = "Best Picture award",
            description = "Returns true if the movie won the Best Picture award")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = BestPictureController.WonResponse.class)))
    })
    @GetMapping("/won")
    ResponseEntity<BestPictureController.WonResponse> wonByTitle(@RequestParam String title);
}
