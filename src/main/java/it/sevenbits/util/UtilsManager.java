package it.sevenbits.util;

/**
 * Created by User on 06.03.14.
 */
public class UtilsManager {
    /**
     * perform given array of Strings to string, inserting space-symbol.
     * @param src  array
     * @return     string
     */
    public static String stringArrayToString(final String[] src) {
        if (src == null) {
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

    /**
     *
     */
 /*   public static String[] stringToTokensArray(final String str) {
        if (str == null) {
            return null;
        }
        /*StringTokenizer token = new StringTokenizer(str);
        String[] words = new String[token.countTokens()];
        for (int i = 0 ; i < words.length ; i++) {
            words[i] = token.nextToken();
        }
        return str.split(" ");
    } */
}
