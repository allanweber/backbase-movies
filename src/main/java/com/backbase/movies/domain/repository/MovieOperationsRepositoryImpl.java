package com.backbase.movies.domain.repository;

import com.backbase.movies.domain.Movie;
import com.backbase.movies.domain.Nominee;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

class MovieOperationsRepositoryImpl implements MovieOperationsRepository {

    private final MongoOperations mongoOperations;

    public MovieOperationsRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void addNominee(String movieId, Nominee nominee) {
        Query query = new Query(Criteria.where("_id").is(movieId));
        Update update = new Update().addToSet("nominees", nominee);
        mongoOperations.updateFirst(query, update, Movie.class);
    }

    @Override
    public boolean wonBestPicture(String movieId) {
        Query query = new Query(Criteria.where("_id").is(movieId).and("nominees.category").is("BEST_PICTURE").and("nominees.won").is(true));
        Movie one = mongoOperations.findOne(query, Movie.class);
        return one != null;
    }

    @Override
    public boolean wonBestPictureByMovieTitle(String movieTitle) {
        Query query = new Query(Criteria.where("title").is(movieTitle).and("nominees.category").is("BEST_PICTURE").and("nominees.won").is(true));
        Movie one = mongoOperations.findOne(query, Movie.class);
        return one != null;
    }
}
