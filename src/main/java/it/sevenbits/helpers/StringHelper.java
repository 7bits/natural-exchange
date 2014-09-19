package it.sevenbits.helpers;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class StringHelper {
    public static String toUpperCase(String src) {
        if (src == null) {
            return "";
        }
        return src.toUpperCase();
    }

    public static boolean isEqualStringWithSpaceSeparator(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return false;
        }
        List<String> stringList1 = getListFromString(str1);
        List<String> stringList2 = getListFromString(str2);
        if (stringList1.size() != stringList2.size()) {
            return false;
        }
        for (String currentString: stringList1) {
            for (int j = 0 ; j  < stringList2.size(); j++) {
                if (currentString.equals( stringList2.get(j) )) {
                    stringList2.remove(j);
                    break;
                }
            }
        }
        if (stringList2.size() != 0) {
            return false;
        }
        return true;
    }

    private static List<String> getListFromString(String str) {
        if (str == null) {
            return null;
        }
        List<String> list = new ArrayList<>();
        String[] separateStrings = StringUtils.split(str);
        for (int i = 0; i < separateStrings.length; i++) {
            list .add(separateStrings[i]);
        }
        return list;
    }
}
