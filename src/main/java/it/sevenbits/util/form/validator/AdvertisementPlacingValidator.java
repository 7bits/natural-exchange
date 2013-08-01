package it.sevenbits.util.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

import it.sevenbits.util.form.AdvertisementPlacingForm;

@Component
public class AdvertisementPlacingValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AdvertisementPlacingForm.class.isAssignableFrom(clazz);
    }

    @Override
	public void validate(Object target, Errors errors) {
        AdvertisementPlacingForm AdvertisementPlacingForm = (AdvertisementPlacingForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.empty", "Title must not be empty.");
        String title = AdvertisementPlacingForm.getTitle();
        if ((title.length()) > 16) {
            errors.rejectValue("title", "title.tooLong", "Title must not more than 16 characters.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "text.empty", "Text must not be empty.");
        String text = AdvertisementPlacingForm.getText();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "photoFile", "photoFile.empty", "You must upload image.");
        String photoFile = AdvertisementPlacingForm.getText();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "category.empty", "Category must not be empty.");
    }
}