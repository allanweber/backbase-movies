package com.backbase.movies.domain.movieapi;

import com.backbase.movies.domain.movieapi.impl.BigDecimalJsonDeserializer;
import com.backbase.movies.domain.movieapi.impl.IntegerJsonDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieEntry {
    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    @JsonDeserialize(using = IntegerJsonDeserializer.class)
    private int year;

    @JsonProperty("BoxOffice")
    @JsonDeserialize(using = BigDecimalJsonDeserializer.class)
    private BigDecimal boxOffice;

    @JsonProperty("Response")
    private boolean response;

    @JsonProperty("Error")
    private String error;

    public MovieEntry() {
    }

    public MovieEntry(String title, int year, BigDecimal boxOffice) {
        this.title = title;
        this.year = year;
        this.boxOffice = boxOffice;
    }

    public MovieEntry(String title, int year, BigDecimal boxOffice, boolean response) {
        this.title = title;
        this.year = year;
        this.boxOffice = boxOffice;
        this.response = response;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public BigDecimal getBoxOffice() {
        return boxOffice;
    }

    public boolean isResponse() {
        return response;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "MovieEntry{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", boxOffice=" + boxOffice +
                ", response=" + response +
                ", error='" + error + '\'' +
                '}';
    }
}
