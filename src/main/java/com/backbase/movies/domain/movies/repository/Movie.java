package com.backbase.movies.domain.movies.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "movies")
public class Movie {

    @Id
    private String id;

    private String title;

    private int year;

    private MovieRate rate;

    private List<Nominee> nominees;

    private BigDecimal boxOffice;

    public Movie() {
        this.rate = new MovieRate();
    }

    public Movie(String id, String title, int year) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    public Movie(String title, int year) {
        this();
        this.title = title;
        this.year = year;
    }


    public Movie(String title, int year, BigDecimal boxOffice) {
        this();
        this.title = title;
        this.year = year;
        this.boxOffice = boxOffice;
    }

    public Movie(String title, int year, List<Nominee> nominees) {
        this();
        this.title = title;
        this.year = year;
        this.nominees = nominees;
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

    public BigDecimal getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(BigDecimal boxOffice) {
        this.boxOffice = boxOffice;
    }

    public List<Nominee> getNominees() {
        return nominees;
    }
}
