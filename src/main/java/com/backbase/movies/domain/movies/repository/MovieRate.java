package com.backbase.movies.domain.movies.repository;

import com.backbase.movies.domain.movies.Helper;

public class MovieRate {
    private int ratings = 0;

    private double rate = 0.0;

    public void rate(double rate) {
        this.ratings++;
        this.rate = Helper.doublePrecision((this.rate + rate) / this.ratings);
    }

    public int getRatings() {
        return ratings;
    }

    public double getRate() {
        return rate;
    }
}
