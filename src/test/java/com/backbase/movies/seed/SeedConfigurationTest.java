package com.backbase.movies.seed;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;


@EnableConfigurationProperties(SeedConfiguration.class)
@TestPropertySource(properties = {"moviesapi.seed.enabled=true"})
@ExtendWith(SpringExtension.class)
class SeedConfigurationTest {

    @Autowired
    SeedConfiguration properties;

    @DisplayName("Storage properties are filled")
    @Test
    void storage() {
        assertTrue(properties.isEnabled());
    }
}