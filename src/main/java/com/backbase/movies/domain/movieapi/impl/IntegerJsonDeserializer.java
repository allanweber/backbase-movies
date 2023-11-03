package com.backbase.movies.domain.movieapi.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class IntegerJsonDeserializer extends StdDeserializer<Integer> {

    static Logger logger = LoggerFactory.getLogger(IntegerJsonDeserializer.class);

    protected IntegerJsonDeserializer(Class<?> vc) {
        super(vc);
    }

    public IntegerJsonDeserializer() {
        this(null);
    }

    @Override
    public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String regexPattern = "(\\d{4}).*";
        String cleanedString = jsonParser.getText().replaceFirst(regexPattern, "$1");

        try {
            return Integer.parseInt(cleanedString);
        } catch (NumberFormatException e) {
            logger.error("Error parsing integer value: {}", jsonParser.getText());
            return 0;
        }
    }
}
