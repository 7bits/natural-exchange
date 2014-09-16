package it.sevenbits.helpers.jadeHelpers;

public class StringHelper {
    public static String toUpperCase(String src) {
        if (src == null) {
            return "";
        }
        return src.toUpperCase();
    }
}
