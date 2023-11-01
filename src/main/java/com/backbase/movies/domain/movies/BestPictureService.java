package com.backbase.movies.domain.movies;

import com.backbase.movies.domain.movies.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class BestPictureService {

    private final MovieRepository movieRepository;

    public BestPictureService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public boolean wonBestPicture(String movieId) {
        return movieRepository.wonBestPicture(movieId);
    }
}
