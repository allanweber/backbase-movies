package com.backbase.movies.seed;

public class SeedParser {

    protected final SeedRecord seedRecord;

    public SeedParser(SeedRecord seedRecord) {
        this.seedRecord = seedRecord;
    }

    protected int getYear() {
        String yearString = seedRecord.year().replaceFirst("^(\\d{4}).*", "$1");
        return Integer.parseInt(yearString);
    }

    protected String getMovieTitle() {
        return seedRecord.nominee().trim();
    }

    protected boolean won() {
        return "yes".equalsIgnoreCase(seedRecord.won());
    }

    protected String getAdditionalInfo() {
        return seedRecord.additionalInfo();
    }
}
