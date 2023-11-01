package com.backbase.movies.domain.repository;

import com.backbase.movies.domain.Nominee;

interface MovieOperationsRepository {
    void addNominee(String movieId, Nominee nominee);
}
