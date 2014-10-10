package it.sevenbits.util.form.validator.validationMethods;

public class CheckingLength {
    private final static int MAX_TAG_LENGTH = 20;

    /**
     * @return true if tags have too long tag else return false.
     * */
    public static boolean validateTagsForTooLongTag(final String tags) {
        int tagLength = 0;
        for (int i = 0; i < tags.length(); i++) {
            if (tags.charAt(i) != ' ') {
                tagLength++;
                if (tagLength == MAX_TAG_LENGTH) {
                    return true;
                }
            } else {
                tagLength = 0;
            }
        }
        return false;
    }
}
