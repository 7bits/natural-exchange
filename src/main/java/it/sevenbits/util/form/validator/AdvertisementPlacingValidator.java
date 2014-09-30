package it.sevenbits.util.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import it.sevenbits.util.form.AdvertisementPlacingForm;

/**
 * Validator for advertisement placing form
 */
@Component
public class AdvertisementPlacingValidator implements Validator {
    private final static int maxTitleLength = 16;
    private final static int maxAdvertisementTextLength = 200;

    @Override
    public boolean supports(final Class<?> clazz) {
        return AdvertisementPlacingForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        AdvertisementPlacingForm advertisementPlacingForm = (AdvertisementPlacingForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.empty", "Заполните поле заголовка.");
        String title = advertisementPlacingForm.getTitle();
        String text = advertisementPlacingForm.getText();
        if ((title.length()) > maxTitleLength) {
            errors.rejectValue("title", "title.tooLong", "Недопустимо больше 16 знаков в заголовке.");
        }
        if ((text.length()) > maxAdvertisementTextLength) {
            errors.rejectValue("text", "text.tooLong", "Недопустимо больше 200 знаков в описании.");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "text", "text.empty", "Описание не должно быть пустым."
        );

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "category.empty", "Выберите категорию.");
    }
}