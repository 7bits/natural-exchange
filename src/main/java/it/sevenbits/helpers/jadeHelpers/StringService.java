package it.sevenbits.helpers.jadeHelpers;

public class StringService {
    public static String toUpperCase(String src) {
        if (src == null) {
            return "";
        }
        return src.toUpperCase();
    }
}
