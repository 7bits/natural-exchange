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
    private final static int maxTitleLength = 16;

    @Override
    public boolean supports(final Class<?> clazz) {
        return AdvertisementEditingForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        AdvertisementEditingForm advertisementEditingForm = (AdvertisementEditingForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.empty", "Заполните поле заголовка.");
        String title = advertisementEditingForm.getTitle();
        if ((title.length()) > maxTitleLength) {
            errors.rejectValue("title", "title.tooLong", "Недопустимо больше 16 знаков.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "text", "text.empty", "Информация не должна быть пуста."
        );

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "category.empty", "Выберите категорию.");
    }
}