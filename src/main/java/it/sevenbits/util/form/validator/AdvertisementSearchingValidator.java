package it.sevenbits.util.form.validator;

import it.sevenbits.dao.SearchVariantDao;
import it.sevenbits.entity.SearchVariant;
import it.sevenbits.util.UtilsManager;
import it.sevenbits.util.form.AdvertisementSearchingForm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Validator for advertisements searching spring form
 */
@Component
public class AdvertisementSearchingValidator implements Validator {
    private final static int DTAE_LENGTH = 3;
    @Resource(name = "searchVariantDao")
    private SearchVariantDao searchVariantDao;

    @Override
    public boolean supports(final Class<?> clazz) {
        return AdvertisementSearchingForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        String errorDate = "Вводите дату в виде dd.mm.yy";
        AdvertisementSearchingForm advertisementSearchingForm = (AdvertisementSearchingForm) target;
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String email = auth.getName();
//        String[] categories  = advertisementSearchingForm.getCategories();
//        String cats = UtilsManager.stringArrayToString(categories);
//        String keyWords = advertisementSearchingForm.getKeyWords();
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

//        if (cats == null) {
//            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categories", "category.empty", "Выберите категорию.");
//        } else  if (this.searchVariantDao.isExist(new SearchVariant(email, keyWords,cats))) {
//            errors.reject("searchVar.exists", "Такой вариант поиска у вас уже есть.");
//        }
    }

    private boolean validateDate(String date) {
        if (date == null) {
            return false;
        }
        if (date == "") {
            return true;
        }
        StringTokenizer token = new StringTokenizer(date, ".");
        int length = token.countTokens();
        //1 - day, 2 - month, 3 - year
        if (length != this.DTAE_LENGTH) {
            return false;
        }
        List<String> dayMonthYear = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            dayMonthYear.add(token.nextToken());
        }
        for (String currentDate: dayMonthYear) {
            if (!currentDate.matches("[0-9]+")) {
                return false;
            }
        }
        return true;
    }
}