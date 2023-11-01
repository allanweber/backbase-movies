package com.backbase.movies.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie, String> {
    Optional<Movie> getByTitleAndYear(String title, int year);
}
