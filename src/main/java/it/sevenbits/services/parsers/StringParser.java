package it.sevenbits.services.parsers;

import org.apache.commons.lang3.StringUtils;

public class StringParser {
    public static String getType(final String fileName) {
        String[] parts = StringUtils.split(fileName, '.');
        int length = parts.length;
        return parts[length - 1];
    }
}
