package it.sevenbits.util;

import java.util.HashSet;
import java.util.Set;

public class FileValidatorConstants {
    public final static int MAX_FILE_SIZE = 3 * 1024 * 1024;

    public final static Set<String> photoFileTypes;

    static {
        photoFileTypes = new HashSet<>();
        photoFileTypes.add("jpg");
        photoFileTypes.add("jpeg");
        photoFileTypes.add("png");
        photoFileTypes.add("img");
    }
}
