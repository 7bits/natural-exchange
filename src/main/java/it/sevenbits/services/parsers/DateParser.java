package it.sevenbits.services.parsers;

import it.sevenbits.web.controller.MainController;
import it.sevenbits.web.util.DatePair;
import it.sevenbits.web.util.form.advertisement.AdvertisementSearchingForm;
import it.sevenbits.web.util.form.validator.advertisement.AdvertisementSearchingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DateParser {
    private static final long MILLISECONDS_IN_A_DAY = 86400000;

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    public DatePair takeAndValidateDate(String dateFrom, String dateTo, BindingResult bindingResult,
                                        AdvertisementSearchingValidator advertisementSearchingValidator) {
        AdvertisementSearchingForm advertisementSearchingForm = new AdvertisementSearchingForm();
        if (dateFrom != null) {
            advertisementSearchingForm.setDateFrom(dateFrom);
        } else if (advertisementSearchingForm.getDateFrom() == null) {
            advertisementSearchingForm.setDateFrom("");
        }
        if (dateTo != null) {
            advertisementSearchingForm.setDateTo(dateTo);
        } else if (advertisementSearchingForm.getDateTo() == null) {
            advertisementSearchingForm.setDateTo("");
        }
        advertisementSearchingValidator.validate(advertisementSearchingForm, bindingResult);
        String stringDateFrom = advertisementSearchingForm.getDateFrom();
        String stringDateTo = advertisementSearchingForm.getDateTo();
        Long longDateFrom = strDateToUnixTimestamp(stringDateFrom);
        Long longDateTo = strDateToUnixTimestamp(stringDateTo);
        if (longDateTo != null) {
            longDateTo += MILLISECONDS_IN_A_DAY;
        }
        return new DatePair(longDateFrom, longDateTo);
    }

    private static Long strDateToUnixTimestamp(String dt) {
        if (dt.equals("")) {
            return null;
        }
        Date date = null;
        DateFormat formatter = new SimpleDateFormat("dd.MM.yy");
        try {
            date = formatter.parse(dt);
        } catch (ParseException ex) {
            logger.error("Wrong date format");
            ex.printStackTrace();
        }
        return date.getTime();
    }
}
