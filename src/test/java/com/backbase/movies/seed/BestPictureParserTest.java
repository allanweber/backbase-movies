package com.backbase.movies.seed;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class BestPictureParserTest {

    @DisplayName("Should parse the Year")
    @ParameterizedTest
    @ValueSource(strings = {"2019 (92nd)", "2019/33 (6th)"})
    void year(String year) {
        SeedRecord record = new SeedRecord(year, "", "", "", "");
        BestPictureParser bestPictureParser = new BestPictureParser(record);

        int actual = bestPictureParser.getYear();

        assertEquals(2019, actual);
    }

    @DisplayName("Should parse the Movie Title and consider additional info when title is a studio name")
    @ParameterizedTest
    @ValueSource(strings = {"Movie Title", "Feature Productions", "Fox", "Metro-Goldwyn-Mayer", "Paramount Famous Lasky", "The Caddo Company"})
    void movieTitle(String nominee) {
        String additionalInfo = "Movie Title";
        SeedRecord record = new SeedRecord("", "", nominee, additionalInfo, "");
        BestPictureParser bestPictureParser = new BestPictureParser(record);

        String actual = bestPictureParser.getMovieTitle();

        assertEquals("Movie Title", actual);
    }

    @DisplayName("Should parse yes as true for won")
    @ParameterizedTest
    @ValueSource(strings = {"yes", "Yes", "YES"})
    void won(String won) {
        SeedRecord record = new SeedRecord("", "", "", "", won);
        BestPictureParser bestPictureParser = new BestPictureParser(record);

        boolean actual = bestPictureParser.won();

        assertTrue(actual);
    }

    @DisplayName("Should parse no as false for won")
    @ParameterizedTest
    @ValueSource(strings = {"no", "No", "NO"})
    void notWon(String won) {
        SeedRecord record = new SeedRecord("", "", "", "", won);
        BestPictureParser bestPictureParser = new BestPictureParser(record);

        boolean actual = bestPictureParser.won();

        assertFalse(actual);
    }
}