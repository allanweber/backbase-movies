package com.backbase.movies.domain.movies.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie, String>, MovieOperationsRepository {
    Optional<Movie> getByTitleAndYear(String title, int year);

    Optional<Movie> getByTitle(String title);
}
