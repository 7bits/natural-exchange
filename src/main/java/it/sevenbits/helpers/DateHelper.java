package it.sevenbits.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateHelper {
    private final Logger logger = LoggerFactory.getLogger(EncodeDecodeHelper.class);
    private static final String[] MONTHS = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа",
        "сентября", "октября", "ноября", "декабря"};


    public static String encodeToNormalDate(final String notEncodeString) {
        StringBuilder date = new StringBuilder(notEncodeString);
        if (notEncodeString == null) {
            return "";
        }
        for (int i = 0; i < date.length(); i++) {
            if (i == '.') {
                date.subSequence(0, i-2);
                char firstNumberOfDate = notEncodeString.charAt(i+1);
                char secondNumberOfDate = notEncodeString.charAt(i+2);
                String monthDate = String.valueOf(firstNumberOfDate + secondNumberOfDate);
                return MONTHS[Integer.parseInt(monthDate)];
            }
        }
        return "";
    }
}