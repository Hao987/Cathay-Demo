package com.example.demo.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    static final DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    static final DateTimeFormatter newFomatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static String formatDate(String date) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(date, formatter);
        return zonedDateTime.format(newFomatter);
    }
}
