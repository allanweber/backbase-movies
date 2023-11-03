package com.backbase.movies.domain.movieapi.impl;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BigDecimalJsonDeserializerTest {

    BigDecimalJsonDeserializer deserializer = new BigDecimalJsonDeserializer();

    @DisplayName("Given a string value that can be parsed to a BigDecimal, when deserializing, then the BigDecimal value is returned")
    @ParameterizedTest
    @ValueSource(strings = {"$1,000,000.00", "$1,000,000", "1,000,000.00", "1,000,000", "1000000.00", "1000000"})
    void deserialize(String value) throws IOException {
        JsonParser parser = mock(JsonParser.class);
        when(parser.getText()).thenReturn(value);

        BigDecimal result = deserializer.deserialize(parser, null);

        assertEquals(new BigDecimal("1000000.00"), result);
    }

    @DisplayName("Given a string value that cannot be parsed to a BigDecimal, when deserializing, then a BigDecimal with value 0 is returned")
    @ParameterizedTest
    @ValueSource(strings = {"abc", ".,/", "null", ""})
    void deserializeInvalidValue(String value) throws IOException {
        JsonParser parser = mock(JsonParser.class);
        when(parser.getText()).thenReturn(value);

        BigDecimal result = deserializer.deserialize(parser, null);

        assertEquals(BigDecimal.ZERO, result);
    }
}