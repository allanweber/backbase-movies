package com.backbase.movies.domain.imdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MovieEntry(@JsonProperty("Title") String title, @JsonProperty("Year") int year,
                         @JsonProperty("BoxOffice") double boxOffice) {
}
