package com.backbase.movies.domain.movies;

import com.backbase.movies.domain.movieapi.MovieCollectionApi;
import com.backbase.movies.domain.movieapi.MovieEntry;
import com.backbase.movies.domain.movies.repository.Movie;
import com.backbase.movies.domain.movies.repository.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    MovieCollectionApi movieCollectionApi;

    @InjectMocks
    RatingService ratingService;

    @DisplayName("Rate a movie that is already in the database including the box office. Should not call the movie collection API")
    @Test
    void rate() {
        String movieTitle = "The Godfather";
        double rate = 10.0;

        when(movieRepository.getByTitle(movieTitle)).thenReturn(of(new Movie(movieTitle, 1972, BigDecimal.valueOf(1000))));
        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Movie movie = ratingService.rate(movieTitle, rate);

        assertEquals(movieTitle, movie.getTitle());
        assertEquals(1972, movie.getYear());
        assertEquals(1000, movie.getBoxOffice().doubleValue());

        verify(movieCollectionApi, never()).searchMovie(movieTitle);
    }

    @DisplayName("Rate a movie that is already in the database but lacks the box office, must call the movie collection API")
    @Test
    void rateNoBoxOffice() {
        String movieTitle = "The Godfather";
        double rate = 10.0;

        when(movieRepository.getByTitle(movieTitle)).thenReturn(of(new Movie(movieTitle, 1972, null)));
        when(movieCollectionApi.searchMovie(movieTitle)).thenReturn(of(new MovieEntry(movieTitle, 1972, BigDecimal.valueOf(1000))));
        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Movie movie = ratingService.rate(movieTitle, rate);

        assertEquals(movieTitle, movie.getTitle());
        assertEquals(1972, movie.getYear());
        assertEquals(1000, movie.getBoxOffice().doubleValue());
    }

    @DisplayName("Rate a movie that is new the database, must call the movie collection API and set all the fields")
    @Test
    void rateNewMovie() {
        String movieTitle = "The Godfather";
        double rate = 10.0;

        when(movieRepository.getByTitle(movieTitle)).thenReturn(empty());
        when(movieCollectionApi.searchMovie(movieTitle)).thenReturn(of(new MovieEntry(movieTitle, 1972, BigDecimal.valueOf(1000))));
        when(movieRepository.save(any(Movie.class)))
                .thenAnswer(invocation -> invocation.getArgument(0))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Movie movie = ratingService.rate(movieTitle, rate);

        assertEquals(movieTitle, movie.getTitle());
        assertEquals(1972, movie.getYear());
        assertEquals(1000, movie.getBoxOffice().doubleValue());
    }

    @DisplayName("Rate a movie is not in the database and is not in the movie collection API, must throw an exception and don't execute any save")
    @Test
    void movieNotFound() {
        String movieTitle = "The Godfather";
        double rate = 10.0;

        when(movieRepository.getByTitle(movieTitle)).thenReturn(empty());
        when(movieCollectionApi.searchMovie(movieTitle)).thenReturn(empty());

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> ratingService.rate(movieTitle, rate));

        assertEquals("Movie The Godfather not found", exception.getStatusText());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
            verify(movieRepository, never()).save(any(Movie.class));
    }

    @DisplayName("Get top rated movies")
    @Test
    void topRated() {
        int limit = 10;
        when(movieRepository.topRated(limit)).thenReturn(List.of(
                new Movie("The Godfather", 1972, BigDecimal.valueOf(1000)),
                new Movie("The Godfather: Part II", 1974, BigDecimal.valueOf(1000))
        ));

        List<Movie> movies = ratingService.topRated(limit);

        assertEquals(2, movies.size());
        verify(movieRepository).topRated(limit);
    }
}