package com.backbase.movies;

import com.backbase.movies.seed.SeedConfiguration;
import com.backbase.movies.seed.SeedCsv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class MoviesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesApiApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(SeedConfiguration seedConfiguration, SeedCsv seedCsv){
        return args -> {
            if(seedConfiguration.isEnabled()) {
                seedCsv.seed();
            } else {
                System.out.println("Not seeding database");
            }
        };
    }

}