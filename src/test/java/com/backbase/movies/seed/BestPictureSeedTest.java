package com.backbase.movies.seed;

import com.backbase.movies.domain.movies.MovieService;
import com.backbase.movies.domain.movies.repository.Category;
import com.backbase.movies.domain.movies.repository.Movie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BestPictureSeedTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    BestPictureSeed bestPictureSeed;

    @DisplayName("Seed a best picture movie")
    @Test
    void best(){
        SeedRecord record = new SeedRecord("2019", Category.BEST_PICTURE.getCsvValue(),"Avatar", "extra info", "yes");

        when(movieService.getOrCreateMovie(argThat(movie -> movie.getYear() == 2019 && movie.getTitle().equals("Avatar"))))
                .thenReturn(new Movie("1","Avatar", 2019));

        doNothing().when(movieService).addNominee(eq("1"),
                argThat(nominee -> nominee.getCategory().equals(Category.BEST_PICTURE) && nominee.getNominee().equals("extra info") && nominee.isWon()));

        bestPictureSeed.seed(Stream.of(record).toList());
    }

    @DisplayName("Seed a non best picture movie")
    @Test
    void notBest(){
        SeedRecord record = new SeedRecord("2019", Category.BEST_PICTURE.getCsvValue(),"Avatar", "extra info", "no");

        when(movieService.getOrCreateMovie(argThat(movie -> movie.getYear() == 2019 && movie.getTitle().equals("Avatar"))))
                .thenReturn(new Movie("1","Avatar", 2019));

        doNothing().when(movieService).addNominee(eq("1"),
                argThat(nominee -> nominee.getCategory().equals(Category.BEST_PICTURE) && nominee.getNominee().equals("extra info") && !nominee.isWon()));

        bestPictureSeed.seed(Stream.of(record).toList());
    }

    @DisplayName("Seed a best picture movie where the movie tile is a studio name and must swap with additional info")
    @Test
    void bestStudio(){
        SeedRecord record = new SeedRecord("2019", Category.BEST_PICTURE.getCsvValue(),"Feature Productions", "Avatar", "yes");

        when(movieService.getOrCreateMovie(argThat(movie -> movie.getYear() == 2019 && movie.getTitle().equals("Avatar"))))
                .thenReturn(new Movie("1","Avatar", 2019));

        doNothing().when(movieService).addNominee(eq("1"),
                argThat(nominee -> nominee.getCategory().equals(Category.BEST_PICTURE) && nominee.getNominee().equals("Feature Productions") && nominee.isWon()));

        bestPictureSeed.seed(Stream.of(record).toList());
    }
}