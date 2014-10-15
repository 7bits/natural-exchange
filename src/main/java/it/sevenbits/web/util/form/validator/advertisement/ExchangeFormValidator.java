package it.sevenbits.web.util.form.validator.advertisement;

import it.sevenbits.web.util.form.advertisement.ExchangeForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

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
            errors.rejectValue("exchangePropose", "exchangePropose.tooLong", "Недопустимо больше 150 знаков.");
        }
    }
}