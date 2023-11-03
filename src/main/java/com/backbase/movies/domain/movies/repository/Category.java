package com.backbase.movies.domain.movies.repository;

public enum Category {
    BEST_PICTURE("Best Picture"),

    //TODO: IMPLEMENT MAYBE ONE DAY
    ACTOR_LEADING("Actor -- Leading Role");
    //.....

    private final String csvValue;

    Category(String csvValue) {
        this.csvValue = csvValue;
    }

    public String getCsvValue() {
        return csvValue;
    }
}


