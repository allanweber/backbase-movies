package com.backbase.movies.domain.movieapi;

import com.backbase.movies.domain.movieapi.impl.DoubleJsonDeserializer;
import com.backbase.movies.domain.movieapi.impl.IntegerJsonDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieEntry {
    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    @JsonDeserialize(using = IntegerJsonDeserializer.class)
    private int year;

    @JsonProperty("BoxOffice")
    @JsonDeserialize(using = DoubleJsonDeserializer.class)
    private double boxOffice;

    @JsonProperty("Response")
    private boolean response;

    @JsonProperty("Error")
    private String error;

    public MovieEntry() {
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public double getBoxOffice() {
        return boxOffice;
    }

    public boolean isResponse() {
        return response;
    }

    public String getError() {
        return error;
    }
}
