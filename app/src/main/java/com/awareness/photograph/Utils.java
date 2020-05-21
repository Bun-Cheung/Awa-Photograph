package com.awareness.photograph;

import android.icu.text.DateFormat;

import java.util.Date;

public class Utils {
    public static String getCurrentDate() {
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
        return format.format(new Date(System.currentTimeMillis()));
    }
}
