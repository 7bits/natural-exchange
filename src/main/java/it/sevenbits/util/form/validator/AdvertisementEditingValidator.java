package it.sevenbits.util.form.validator;

import it.sevenbits.util.form.AdvertisementEditingForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

/**
 * Validator for advertisement placing form
 */
@Component
public class AdvertisementEditingValidator implements Validator {
    private final static int MAX_TITLE_LENGTH = 100;
    private final static int MAX_TAGS_LENGTH = 100;
    private final static int MAX_TAG_LENGTH = 20;
    private final static int MAX_ADVERTISEMENT_TEXT_LENGTH = 500;

    @Override
    public boolean supports(final Class<?> clazz) {
        return AdvertisementEditingForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        AdvertisementEditingForm advertisementEditingForm = (AdvertisementEditingForm) target;
        String title = advertisementEditingForm.getTitle();
        String text = advertisementEditingForm.getText();
        String tags = advertisementEditingForm.getTags();

        if (tags.length() > MAX_TAGS_LENGTH) {
            errors.rejectValue("tags", "tags.tooLong", "Слишком много тегов у объявления. Пожалуйста, уберите несколько тегов.");
        }
        if (validateTagsForTooLongTag(tags)) {
            errors.rejectValue("tags", "tags.tooLongTag", "Недопустим тег длиннее 20 символов.");
        }
        if ((title.length()) > MAX_TITLE_LENGTH) {
            errors.rejectValue("title", "title.tooLong", "Недопустимо больше 100 знаков в заголовке.");
        }
        if ((text.length()) > MAX_ADVERTISEMENT_TEXT_LENGTH) {
            errors.rejectValue("text", "text.tooLong", "Недопустимо больше 500 знаков в описании.");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "text", "text.empty", "Описание не должно быть пустым."
        );
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.empty", "Заполните поле заголовка.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "category.empty", "Выберите категорию.");
    }

    /**
     * @return true if tags have too long tag else return false.
     * */
    private boolean validateTagsForTooLongTag(final String tags) {
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