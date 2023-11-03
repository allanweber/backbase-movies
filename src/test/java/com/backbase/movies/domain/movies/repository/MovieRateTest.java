package com.backbase.movies.domain.movies.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieRateTest {

    @DisplayName("Rate a movie never rated before the current rate is the new rate")
    @Test
    void test() {
        MovieRate movieRate = new MovieRate();

        movieRate.rate(5.0);

        assertEquals(5.0, movieRate.getCurrentRate());
    }

    @DisplayName("Rate a movie already rated before the current rate is the average of the new and old rate")
    @Test
    void test2() {
        MovieRate movieRate = new MovieRate();
        movieRate.rate(5.0);
        movieRate.rate(3.5);
        movieRate.rate(4.7);
        movieRate.rate(8.3);

        assertEquals(5.4, movieRate.getCurrentRate());
    }

    @DisplayName("Rate a movie with negative rate, the rate will be Zero")
    @Test
    void zero() {
        MovieRate movieRate = new MovieRate();
        movieRate.rate(-2);

        assertEquals(0.0, movieRate.getCurrentRate());
    }

    @DisplayName("Rate a movie with rate bigger than 10, the rate will be 10")
    @Test
    void ten() {
        MovieRate movieRate = new MovieRate();
        movieRate.rate(10.5);

        assertEquals(10.0, movieRate.getCurrentRate());
    }
}