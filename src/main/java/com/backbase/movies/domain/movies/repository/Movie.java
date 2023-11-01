package com.backbase.movies.domain.movies.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "movies")
public class Movie {

    @Id
    private String id;

    private String title;

    private int year;

    private MovieRate rate;

    private List<Nominee> nominees;

    public Movie() {
    }

    public Movie(String id, String title, int year) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    public Movie(String title, int year) {
        this.title = title;
        this.year = year;
        this.rate = new MovieRate();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public MovieRate getRate() {
        return rate;
    }
}
