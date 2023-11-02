package com.backbase.movies.domain.movieapi.impl;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DoubleJsonDeserializer extends StdDeserializer<Double> {

    static Logger logger = LoggerFactory.getLogger(DoubleJsonDeserializer.class);

    protected DoubleJsonDeserializer(Class<?> vc) {
        super(vc);
    }

    protected DoubleJsonDeserializer() {
        this(null);
    }

    @Override
    public Double deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String cleanedString = jsonParser.getText().replaceAll("[^0-9.]", "");
        try {
            return Double.parseDouble(cleanedString);
        } catch (NumberFormatException e) {
            logger.error("Error parsing double value: {}", jsonParser.getText());
            return 0.0;
        }

    }
}