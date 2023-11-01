package com.backbase.movies.domain.repository;

import com.backbase.movies.domain.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie, String>, MovieOperationsRepository {
    Optional<Movie> getByTitleAndYear(String title, int year);
}
