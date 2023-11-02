package com.backbase.movies.domain.movies.repository;

import java.util.List;

interface MovieOperationsRepository {
    void addNominee(String movieId, Nominee nominee);

    boolean wonBestPicture(String movieTitle);

    List<Movie> topRated(int limit);
}
