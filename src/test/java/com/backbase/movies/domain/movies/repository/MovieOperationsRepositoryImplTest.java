package com.backbase.movies.domain.movies.repository;

import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieOperationsRepositoryImplTest {

    @Mock
    MongoOperations mongoOperations;

    @InjectMocks
    MovieOperationsRepositoryImpl movieOperationsRepository;

    @DisplayName("Add a new Nominee to a movie")
    @Test
    void addNominee() {
        String movieId = "movieId";
        Nominee nominee = new Nominee(Category.BEST_PICTURE, "movieTitle", true);

        when(mongoOperations.updateFirst(
                Query.query(Criteria.where("_id").is(movieId)),
                new Update().addToSet("nominees", nominee),
                Movie.class
        )).thenReturn(UpdateResult.acknowledged(1, 1L, null));

        movieOperationsRepository.addNominee(movieId, nominee);
    }

    @DisplayName("Given a movie that won the Best Picture, return true")
    @Test
    void wonBestPicture() {
        String movieTitle = "movieTitle";

        Query query = Query.query(
                Criteria.where("title").is(movieTitle)
                        .and("nominees.category").is("BEST_PICTURE")
                        .and("nominees.won").is(true)
        );
        when(mongoOperations.findOne(query, Movie.class)).thenReturn(new Movie());

        boolean wonBestPicture = movieOperationsRepository.wonBestPicture(movieTitle);
        assertTrue(wonBestPicture);
    }

    @DisplayName("Given a movie that did not win the Best Picture, return false")
    @Test
    void noBestPicture() {
        String movieTitle = "movieTitle";

        Query query = Query.query(
                Criteria.where("title").is(movieTitle)
                        .and("nominees.category").is("BEST_PICTURE")
                        .and("nominees.won").is(true)
        );
        when(mongoOperations.findOne(query, Movie.class)).thenReturn(null);

        boolean wonBestPicture = movieOperationsRepository.wonBestPicture(movieTitle);
        assertFalse(wonBestPicture);
    }

    @DisplayName("Given a limit, return the top rated movies")
    @Test
    void topRated() {
        int limit = 10;
        when(mongoOperations.aggregate(
                argThat(aggregation -> aggregation.toString().contains("{ \"$sort\" : { \"rate.currentRate\" : -1}}, { \"$limit\" : 10}, { \"$sort\" : { \"boxOffice\" : -1}}")),
                eq(Movie.class),
                eq(Movie.class)
        )).thenReturn(new AggregationResults<>(List.of(new Movie()), Document.parse("{}")));

        List<Movie> topRated = movieOperationsRepository.topRated(limit);
        assertEquals(1, topRated.size());
    }
}