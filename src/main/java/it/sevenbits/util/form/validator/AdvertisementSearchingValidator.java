package it.sevenbits.util.form.validator;

import it.sevenbits.util.form.AdvertisementSearchingForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator for advertisements searching spring form
 */
@Component
public class AdvertisementSearchingValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return AdvertisementSearchingForm.class.isAssignableFrom(clazz);
    }

    @Override
	public void validate(final Object target, final Errors errors) {
        AdvertisementSearchingForm advertisementSearchingForm = (AdvertisementSearchingForm) target;
    }
}