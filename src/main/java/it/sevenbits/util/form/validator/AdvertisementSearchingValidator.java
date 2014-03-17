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

/**
 * Validator for advertisements searching spring form
 */
@Component
public class AdvertisementSearchingValidator implements Validator {
    @Resource(name = "searchVariantDao")
    private SearchVariantDao searchVariantDao;

    @Override
    public boolean supports(final Class<?> clazz) {
        return AdvertisementSearchingForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        AdvertisementSearchingForm advertisementSearchingForm = (AdvertisementSearchingForm) target;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        String[] categories  = advertisementSearchingForm.getCategories();
        String cats = UtilsManager.stringArrayToString(categories);
        String keyWords = advertisementSearchingForm.getKeyWords();
        if (cats == null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categories", "category.empty", "Выберите категорию.");
        } else  if (this.searchVariantDao.isExist(new SearchVariant(email, keyWords,cats))) {
            errors.reject("searchVar.exists", "Такой вариант поиска у вас уже есть.");
        }
    }
}