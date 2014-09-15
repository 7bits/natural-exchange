package it.sevenbits.helpers.helpers;

public class StringService {
    public static String toUpperCase(String src) {
        if (src == null) {
            return "";
        }
        return src.toUpperCase();
    }
}
