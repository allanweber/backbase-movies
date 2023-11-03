package com.backbase.movies.domain.movieapi.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalJsonDeserializer extends StdDeserializer<BigDecimal> {

    private static Logger logger = LoggerFactory.getLogger(BigDecimalJsonDeserializer.class);

    protected BigDecimalJsonDeserializer(Class<?> vc) {
        super(vc);
    }

    protected BigDecimalJsonDeserializer() {
        this(null);
    }

    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        String cleanedString = jsonParser.getText().replaceAll("[^0-9.]", "");
        try {
            bigDecimal =  new BigDecimal(cleanedString).setScale(2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            logger.error("Error parsing double value: {}", jsonParser.getText());
        }
        return bigDecimal;
    }
}