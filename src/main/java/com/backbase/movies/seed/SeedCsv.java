package com.backbase.movies.seed;

import com.backbase.movies.domain.movies.repository.Category;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class SeedCsv {

    private final Logger log = LoggerFactory.getLogger(SeedCsv.class);

    private final BestPictureSeed bestPictureSeed;

    public SeedCsv(BestPictureSeed bestPictureSeed) {
        this.bestPictureSeed = bestPictureSeed;
    }

    public void seed() {

        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("data/academy_awards.csv")) {
            if (is == null) {
                throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot find resource file data/academy_awards.csv");
            }

            CSVFormat csvFormat = CSVFormat.Builder.create().setHeader("Year", "Category", "Nominee", "Additional Info", "Won?").build();
            try (CSVParser csvParser = new CSVParser(new InputStreamReader(is, StandardCharsets.UTF_8), csvFormat)) {
                List<CSVRecord> vscRecords = csvParser.getRecords();
                log.info("Loaded {} records from csv", vscRecords.size());
                List<SeedRecord> records = vscRecords.stream()
                        .filter(record -> record.get("Category").equals(Category.BEST_PICTURE.getCsvValue()))
                        .map(record -> new SeedRecord(record.get("Year"), record.get("Category"), record.get("Nominee"), record.get("Additional Info"), record.get("Won?")))
                        .toList();
                log.info("Loaded {} records for best picture", records.size());

                bestPictureSeed.seed(records);
            }
        } catch (IOException e) {
            throw (HttpClientErrorException) new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Error seeding data").initCause(e);
        }
    }
}
