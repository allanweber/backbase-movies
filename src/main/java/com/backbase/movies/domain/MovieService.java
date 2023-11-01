package com.backbase.movies.domain;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    private final MongoOperations mongoOperations;

    public MovieService(MovieRepository movieRepository, MongoOperations mongoOperations) {
        this.movieRepository = movieRepository;
        this.mongoOperations = mongoOperations;
    }

    public Movie getOrCreateMovie(Movie movie) {
        return movieRepository.getByTitleAndYear(movie.getTitle(), movie.getYear())
                .orElseGet(() -> movieRepository.save(movie));
    }

    public void addNominee(String movieId, Nominee nominee) {
        Query query = new Query(Criteria.where("_id").is(movieId));
        Update update = new Update().addToSet("nominees", nominee);
        mongoOperations.updateFirst(query, update, Movie.class);
    }
}
