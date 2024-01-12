package eu.telecomnancy.labfx.controller.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class DateUtil {
    public static final String DATE_PATTERN = "dd/MM/yyyy";

    public static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static LocalDate start (ArrayList<LocalDate> dates) {
        LocalDate start = dates.get(0);
        for (LocalDate date : dates) {
            if (date.isBefore(start)) {
                start = date;
            }
        }
        return start;
    }

    public static LocalDate end (ArrayList<LocalDate> dates) {
        LocalDate end = dates.get(0);
        for (LocalDate date : dates) {
            if (date.isAfter(end)) {
                end = date;
            }
        }
        return end;
    }
}