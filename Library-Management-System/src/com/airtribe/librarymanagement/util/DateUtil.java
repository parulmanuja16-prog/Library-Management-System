package com.airtribe.librarymanagement.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Utility methods for parsing and formatting appointment dates and times.
 */
public class DateUtil {

    /**
     * Parses a date string in either dd-MM-yyyy or ISO format.
     *
     * @param string the date string to parse
     * @return the parsed LocalDate, or null when the input is empty
     * @throws DateTimeParseException when parsing fails
     */
    static public LocalDate parseString(String string) throws DateTimeParseException{
        if (string == null || string.isEmpty()) {
            return null;
        }

        String trimmed = string.trim();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(trimmed, formatter);
        } catch (DateTimeParseException ex) {
            try {
                return LocalDate.parse(trimmed);
            } catch (DateTimeParseException ignored) {
                throw ex;
            }
        }
    }

    /**
     * Parses a time string in HH:mm format.
     *
     * @param string the time string to parse
     * @return the parsed LocalTime, or null when the input is empty
     * @throws DateTimeParseException when parsing fails
     */
    static public LocalTime parseTime(String string) throws DateTimeParseException{
        if(!string.isEmpty()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime time = LocalTime.parse(string, formatter);       
            return time;
        }else 
            return null;
    }

    /**
     * Formats a LocalDate to dd-MM-yyyy string form.
     *
     * @param date the date to format
     * @return formatted date string or empty string when date is null
     */
    static public String dateToString(LocalDateTime date){
        if(null != date){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return formatter.format(date);
        }
        else
            return "";
    }

    /**
     * Formats a LocalTime to HH:mm string form.
     *
     * @param time the time to format
     * @return formatted time string or empty string when time is null
     */
    static public String timeToString(LocalTime time){
        if(null != time){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return formatter.format(time);
        }else
            return "";
    }

}
