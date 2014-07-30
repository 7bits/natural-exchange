package it.sevenbits.util.form.validator;

import it.sevenbits.util.form.ExchangeForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import it.sevenbits.util.form.AdvertisementPlacingForm;

/**
 * Validator for advertisement placing form
 */
@Component
public class ExchangeFormValidator implements Validator {
    private final static int maxProposeLength = 150;

    @Override
    public boolean supports(final Class<?> clazz) {
        return ExchangeForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        ExchangeForm exchangeForm = (ExchangeForm) target;
        ValidationUtils.rejectIfEmpty(errors, "idExchangeOfferAdvertisement", "idExchangeOfferAdvertisement.empty",
            "Пожалуйста, предложите владельцу вещи одну из своих вещей.");
        String exchangePropose = exchangeForm.getExchangePropose();
        if (exchangePropose.length() > maxProposeLength) {
            errors.rejectValue("exchangePropose", "exchangePropose.tooLong", "Недопустимо больше 150 знаков");
        }
    }
}