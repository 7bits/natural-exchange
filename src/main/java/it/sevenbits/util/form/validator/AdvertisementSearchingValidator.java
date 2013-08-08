package it.sevenbits.util.form.validator;

import it.sevenbits.util.form.AdvertisementSearchingForm;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AdvertisementSearchingValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AdvertisementSearchingForm.class.isAssignableFrom(clazz);
    }

    @Override
	public void validate(Object target, Errors errors) {
        AdvertisementSearchingForm AdvertisementSearchingForm = (AdvertisementSearchingForm) target;

//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "category.empty", "Выберите категорию.");
//        String category = AdvertisementSearchingForm.getCategory();
    }
}