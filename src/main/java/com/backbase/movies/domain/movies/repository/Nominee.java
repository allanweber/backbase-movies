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

    public Category getCategory() {
        return category;
    }

    public String getNominee() {
        return nominee;
    }

    public boolean isWon() {
        return won;
    }
}
