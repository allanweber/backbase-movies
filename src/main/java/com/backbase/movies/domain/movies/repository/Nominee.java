package com.backbase.movies.domain.movies.repository;

public class Nominee {
    private Category category;

    private String nominee;

    private boolean won;

    public Nominee(Category category, String nominee, boolean won) {
        this.category = category;
        this.nominee = nominee;
        this.won = won;
    }

    public Nominee() {
    }
}
