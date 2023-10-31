package com.backbase.movies.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    Optional<Movie> getByTitleAndYear(String title, int year);
}
