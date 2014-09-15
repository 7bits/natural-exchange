package it.sevenbits.util.form.validator;

import it.sevenbits.util.form.SearchEditForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

@Component
public class SearchEditValidator implements Validator {
    private final static int maxTitleLength = 16;

    @Override
    public boolean supports(final Class<?> clazz) {
        return SearchEditForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        SearchEditForm advertisementPlacingForm = (SearchEditForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "category.empty", "Выберите категорию.");
    }
}
