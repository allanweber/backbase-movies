package com.backbase.movies;

import com.backbase.movies.seed.SeedConfiguration;
import com.backbase.movies.seed.SeedCsv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class MoviesApiApplication {

    private final Logger log = LoggerFactory.getLogger(MoviesApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MoviesApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(SeedConfiguration seedConfiguration, SeedCsv seedCsv) {
        return args -> {
            if (seedConfiguration.isEnabled()) {
                seedCsv.seed();
            } else {
                log.info("Not seeding database");
            }
        };
    }

}