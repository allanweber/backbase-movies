package com.backbase.movies.domain.movieapi.impl;

import com.fasterxml.jackson.core.JsonParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IntegerJsonDeserializerTest {

    IntegerJsonDeserializer deserializer = new IntegerJsonDeserializer();

    @DisplayName("Given a string value that can be parsed to an Integer, when deserializing, then the Integer value is returned")
    @ParameterizedTest
    @ValueSource(strings = {"2020", "2020-01-01", "2020/2021", "2020.2021", "2020 2021", "2020 (22)", "2020(A)"})
    void deserialize(String value) throws IOException {
        JsonParser parser = mock(JsonParser.class);
        when(parser.getText()).thenReturn(value);

        Integer result = deserializer.deserialize(parser, null);

        assertEquals(2020, result);
    }

    @DisplayName("Given a string value that cannot be parsed to an Integer, when deserializing, then an Integer with value 0 is returned")
    @ParameterizedTest
    @ValueSource(strings = {"abc", ".,/", "null", ""})
    void deserializeInvalidValue(String value) throws IOException {
        JsonParser parser = mock(JsonParser.class);
        when(parser.getText()).thenReturn(value);

        Integer result = deserializer.deserialize(parser, null);

        assertEquals(0, result);
    }
}