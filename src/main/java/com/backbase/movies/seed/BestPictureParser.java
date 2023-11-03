package com.backbase.movies.seed;

import java.util.List;

public class BestPictureParser extends SeedParser {

    private static final List<String> NOT_A_MOVIE = List.of("Feature Productions", "Fox", "Metro-Goldwyn-Mayer", "Paramount Famous Lasky", "The Caddo Company");

    public BestPictureParser(SeedRecord seedRecord) {
        super(seedRecord);
    }

    @Override
    protected String getMovieTitle() {
        String movieTitle = seedRecord.nominee();
        if (NOT_A_MOVIE.contains(movieTitle)) {
            movieTitle = seedRecord.additionalInfo();
        }
        return movieTitle;
    }

    @Override
    protected String getAdditionalInfo() {
        String additionalInfo = seedRecord.additionalInfo();
        if (NOT_A_MOVIE.contains(seedRecord.nominee())) {
            additionalInfo = seedRecord.nominee();
        }
        return additionalInfo;
    }
}
