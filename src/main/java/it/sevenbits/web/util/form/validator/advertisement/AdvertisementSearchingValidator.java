package it.sevenbits.web.util.form.validator.advertisement;

import it.sevenbits.web.util.form.advertisement.AdvertisementSearchingForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Validator for advertisements searching spring form
 */
@Component
public class AdvertisementSearchingValidator implements Validator {
    private final static int DATE_LENGTH = 3;

    Logger logger = LoggerFactory.getLogger(AdvertisementSearchingValidator.class);

    @Override
    public boolean supports(final Class<?> clazz) {
        return AdvertisementSearchingForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        String errorDate = "Вводите дату в виде dd.mm.yy";
        AdvertisementSearchingForm advertisementSearchingForm = (AdvertisementSearchingForm) target;
        String stringDateFrom = advertisementSearchingForm.getDateFrom();
        String stringDateTo = advertisementSearchingForm.getDateTo();
        boolean validateDateFrom = validateDate(stringDateFrom);
        boolean validateDateTo = validateDate(stringDateTo);
        if (!validateDateFrom) {
            errors.rejectValue("dateFrom", "dateFrom", errorDate);
        }
        if (!validateDateTo) {
            errors.rejectValue("dateTo", "dateTo", errorDate);
        }
    }

    private boolean validateDate(String date) {
        if (date == null) {
            return false;
        }
        if (date == "") {
            return true;
        }
        DateFormat formatter = new SimpleDateFormat("dd.MM.yy");
        try {
            formatter.parse(date);
        } catch (ParseException e) {
            logger.error("Wrong date format");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}