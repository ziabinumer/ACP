package utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
    public static Boolean isValidDate(String date) {
        if (date == null || date.isEmpty()) return false;

        try {
            LocalDate.parse(date, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static int calculateAge(String date) {
        if (!isValidDate(date)) return -1;

        LocalDate birthDate = LocalDate.parse(date, FORMATTER);
        LocalDate today = LocalDate.now();
        if (birthDate.isAfter(today)) return -1; 

        return Period.between(birthDate, today).getYears();
    }

}
