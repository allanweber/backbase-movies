package com.backbase.movies.domain.movies.repository;

import com.backbase.movies.domain.movies.DoubleHelper;

import java.util.ArrayList;
import java.util.List;

public class MovieRate {

    private static final double MAX_RATING = 10.0;
    private static final double MIN_RATING = 0.0;

    private List<Double> ratings;

    private double currentRate;

    protected void rate(double rating) {
        double rate = rating;
        if (rate < MIN_RATING) {
            rate = 0;
        }
        if (rate > MAX_RATING) {
            rate = 10;
        }
        if (ratings == null) {
            ratings = new ArrayList<>();
        }
        ratings.add(rate);
        currentRate = DoubleHelper.precision(ratings.stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public double getCurrentRate() {
        return currentRate;
    }
}
