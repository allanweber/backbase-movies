package com.backbase.movies.domain.movies.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

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
    public boolean wonBestPicture(String movieTitle) {
        Query query = new Query(Criteria.where("title").is(movieTitle)
                .and("nominees.category").is("BEST_PICTURE")
                .and("nominees.won").is(true));
        Movie one = mongoOperations.findOne(query, Movie.class);
        return one != null;
    }

    @Override
    public List<Movie> topRated(int limit) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.sort(Sort.Direction.DESC, "rate.currentRate"),
                Aggregation.limit(limit),
                Aggregation.sort(Sort.Direction.DESC, "boxOffice")
        );
        return mongoOperations.aggregate(aggregation, Movie.class, Movie.class).getMappedResults();
    }
}
