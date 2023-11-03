package com.backbase.movies.domain.movies;

import com.backbase.movies.domain.movies.repository.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BestPictureServiceTest {

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    BestPictureService bestPictureService;

    @DisplayName("Should return true if movie won best picture")
    @Test
    void best() {
        String movieId = "movieId";
        when(movieRepository.wonBestPicture(movieId)).thenReturn(true);

        boolean wonBestPicture = bestPictureService.wonBestPicture(movieId);
        assertTrue(wonBestPicture);
    }

    @DisplayName("Should return false if movie did not win best picture")
    @Test
    void notBest() {
        String movieId = "movieId";
        when(movieRepository.wonBestPicture(movieId)).thenReturn(false);

        boolean wonBestPicture = bestPictureService.wonBestPicture(movieId);
        assertFalse(wonBestPicture);
    }
}