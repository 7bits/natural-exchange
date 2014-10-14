package it.sevenbits.util.form.validator.advertisement;

import it.sevenbits.util.form.advertisement.SearchEditForm;
import it.sevenbits.util.form.validator.validationMethods.CheckingLength;
import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

@Component
public class SearchEditValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return SearchEditForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        SearchEditForm searchEditForm = (SearchEditForm) target;
        String keyword = searchEditForm.getKeywords();
        if (CheckingLength.validateTagsForTooLongTag(keyword)) {
            errors.rejectValue("keyword", "keywordTooLong", "Недопустимо больше 20 символов.");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "category.empty", "Выберите категорию.");
    }
}
