package it.sevenbits.web.util;

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

    /**
     *
     * @return date as Long in millis
     */
    public static Long getTime() {
        TimeZone timeZone = TimeZone.getDefault();
        Calendar calendar = new GregorianCalendar(timeZone);
        return calendar.getTimeInMillis();
    }

    /**
     *
     * @param date in millis
     * @return  date as string with date_format_string
     */
    public static String getDateString(final Long date) {
        TimeZone timeZone = TimeZone.getDefault();
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);
        dateFormat.setTimeZone(timeZone);
        Calendar calendar = new GregorianCalendar(timeZone);
        calendar.setTimeInMillis(date);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * @param days to add to current date
     */
    public static Long addDate(final int days) {
        Calendar calendar = new GregorianCalendar(TimeZone.getDefault());
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTimeInMillis();
    }
}
