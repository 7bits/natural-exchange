package it.sevenbits.util;

/**
 * Класс для функций работы со строковыми массивами и другими, если будут нужны
 */
public abstract class UtilsManager {
    /**
     * perform given array of Strings to string, inserting space-symbol.
     * @param src  array
     * @return     string
     */
    public static String stringArrayToString(final String[] src) {
        if (src == null || src.length == 0) {
            return null;
        }
        String dest = src[0];
        /*for (String aSrc : src) {
            dest += aSrc + " ";
        } */
        for (int i=1; i<src.length; i++) {
            dest +=  (" " + src[i]);
        }
        return dest;
    }

 /*   public static String[] stringToTokensArray(final String str) {
        if (str == null) {
            return null;
        }
        return str.split(" ");
    } */
}
