package com.backbase.movies.domain.movies;

import com.backbase.movies.domain.movies.repository.Category;
import com.backbase.movies.domain.movies.repository.Movie;
import com.backbase.movies.domain.movies.repository.MovieRepository;
import com.backbase.movies.domain.movies.repository.Nominee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieService movieService;

    @DisplayName("Save a movie that is not in the database")
    @Test
    void saveNew() {
        Movie movie = new Movie("The Godfather", 1972);
        when(movieRepository.getByTitleAndYear(movie.getTitle(), movie.getYear())).thenReturn(Optional.empty());
        when(movieRepository.save(movie)).thenReturn(movie);

        Movie savedMovie = movieService.getOrCreateMovie(movie);

        assertEquals(movie.getTitle(), savedMovie.getTitle());
    }

    @DisplayName("Save a movie that is already in the database returns the movie queried and don't save it again")
    @Test
    void getExisting() {
        Movie movie = new Movie("The Godfather", 1972);
        when(movieRepository.getByTitleAndYear(movie.getTitle(), movie.getYear())).thenReturn(of(movie));

        Movie savedMovie = movieService.getOrCreateMovie(movie);

        assertEquals(movie.getTitle(), savedMovie.getTitle());
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @DisplayName("Add a nominee to a movie that is already in the database")
    @Test
    void addNominee() {
        String movieId = "1";
        Nominee nominee = new Nominee(Category.BEST_PICTURE, "Actor", false);
        doNothing().when(movieRepository).addNominee(movieId, nominee);

        movieService.addNominee(movieId, nominee);
    }
}