package com.backbase.movies.domain.movies.repository;

import com.backbase.movies.domain.movies.Helper;

import java.util.ArrayList;
import java.util.List;

public class MovieRate {
    private List<Double> ratings;

    private double currentRate = 0.0;

    public void rate(double rating) {
        if (ratings == null) {
            ratings = new ArrayList<>();
        }
        ratings.add(rating);
        currentRate = Helper.doublePrecision(ratings.stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public double getCurrentRate() {
        return currentRate;
    }
}
