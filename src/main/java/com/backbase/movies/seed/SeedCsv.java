package com.backbase.movies.seed;

import com.backbase.movies.domain.Category;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class SeedCsv {

    private final BestPictureSeed bestPictureSeed;

    public SeedCsv(BestPictureSeed bestPictureSeed) {
        this.bestPictureSeed = bestPictureSeed;
    }

    public void seed() {

//        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("data/academy_fake.csv")) {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("data/academy_awards.csv")) {
            if (is == null) throw new RuntimeException("Cannot find resource file /data/academy_awards.csv");

            CSVFormat csvFormat = CSVFormat.Builder.create().setHeader("Year", "Category", "Nominee", "Additional Info", "Won?").build();
            try (CSVParser csvParser = new CSVParser(new InputStreamReader(is, StandardCharsets.UTF_8), csvFormat)) {
                List<SeedRecord> records = csvParser.getRecords().stream()
                        .filter(record -> record.get("Category").equals(Category.BEST_PICTURE.getCsvValue()))
                        .map(record -> new SeedRecord(record.get("Year"), record.get("Category"), record.get("Nominee"), record.get("Additional Info"), record.get("Won?")))
                        .toList();

                bestPictureSeed.seed(records);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
