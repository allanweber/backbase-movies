package com.backbase.movies.domain.movies.repository;

interface MovieOperationsRepository {
    void addNominee(String movieId, Nominee nominee);

    boolean wonBestPicture(String movieTitle);
}
