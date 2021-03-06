package it.sevenbits.web.util;

import org.apache.commons.lang.StringUtils;

public class Conversion {
    public static String[] stringToArray(final String str) {
        if (str == null) {
            return null;
        }
        return StringUtils.split(str);
    }

    public static String arrayToString(final String[] strings) {
        if (strings == null) {
            return null;
        }
        return StringUtils.join(strings, ' ');
    }
}
