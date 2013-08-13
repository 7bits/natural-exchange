package it.sevenbits.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Class for working with dates
 */

public class TimeManager {
    /**
     * Date format string
     */
    public static final String DATE_FORMAT_STRING = "HH:mm dd.MM.yyyy";

    public static Long getTime() {
        TimeZone timeZone = TimeZone.getDefault();
        Calendar calendar = new GregorianCalendar(timeZone);
        return calendar.getTimeInMillis();
    }

    public static String getDateString(final Long date) {
        TimeZone timeZone = TimeZone.getDefault();
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);
        dateFormat.setTimeZone(timeZone);
        Calendar calendar = new GregorianCalendar(timeZone);
        calendar.setTimeInMillis(date);
        return dateFormat.format(calendar.getTime());
    }
}
