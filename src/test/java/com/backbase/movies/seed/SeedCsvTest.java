package com.backbase.movies.seed;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeedCsvTest {

    @Mock
    BestPictureSeed bestPictureSeed;

    @InjectMocks
    SeedCsv seedCsv;

    @DisplayName("Given a CSV to seed data, will seed only 2 best picture movies category")
    @Test
    void seed() {
        doNothing().when(bestPictureSeed).seed(argThat(list -> list.size() == 2));
        seedCsv.seed();

        verify(bestPictureSeed).seed(any());
    }
}