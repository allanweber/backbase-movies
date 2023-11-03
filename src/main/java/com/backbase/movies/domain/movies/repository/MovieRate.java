package com.backbase.movies.domain.movies.repository;

import com.backbase.movies.domain.movies.Helper;

import java.util.ArrayList;
import java.util.List;

public class MovieRate {
    private List<Double> ratings;

    private double currentRate = 0.0;

    public void rate(double rating) {
        if (rating < 0) {
            rating = 0;
        }
        if (rating > 10) {
            rating = 10;
        }
        if (ratings == null) {
            ratings = new ArrayList<>();
        }
        ratings.add(rating);
        currentRate = Helper.precision(ratings.stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public double getCurrentRate() {
        return currentRate;
    }
}
