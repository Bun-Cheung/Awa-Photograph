package com.awareness.photograph;

import android.icu.text.DateFormat;

import java.math.BigDecimal;
import java.util.Date;

public class Utils {
    public static String formatTimestamp(long timestamp) {
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        return format.format(new Date(timestamp));
    }

    public static double formatDouble(double var) {
        return new BigDecimal(var).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
