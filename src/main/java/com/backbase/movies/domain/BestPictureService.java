package com.backbase.movies.domain;

import com.backbase.movies.domain.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class BestPictureService {

    private final MovieRepository movieRepository;

    public BestPictureService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
}
