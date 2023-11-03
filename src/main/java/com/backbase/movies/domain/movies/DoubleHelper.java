package com.backbase.movies.domain.movies;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleHelper {

    public static double precision(double value) {
        BigDecimal bd = new BigDecimal(value).setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
