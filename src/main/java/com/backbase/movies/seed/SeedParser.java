package com.backbase.movies.seed;

import java.util.regex.Pattern;

public abstract class SeedParser {

    private static final Pattern YEAR_PATTERN = Pattern.compile("(\\d{4})");

    private SeedRecord seedRecord;

    public void parse(SeedRecord seedRecord) {
        this.seedRecord = seedRecord;
    }

    protected int getYear() {
        return YEAR_PATTERN.matcher(seedRecord.year())
                .results()
                .map(mr -> mr.group(1))
                .map(Integer::parseInt)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot parse year from " + seedRecord.year()));
    }

    protected String getMovieTitle(){
        return seedRecord.nominee().trim();
    }

    protected boolean won() {
        return seedRecord.won().equalsIgnoreCase("yes");
    }

    protected String getAdditionalInfo(){
        return seedRecord.additionalInfo();
    }
}
