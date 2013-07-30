package it.sevenbits.util.form.validator;

import it.sevenbits.util.form.AdvertisementPlacingForm;
import it.sevenbits.util.form.NewsPostingfForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: sevenbits
 * Date: 7/29/13
 * Time: 3:21 PM
 *
 */
@Component
public class NewsPostingValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return NewsPostingfForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NewsPostingfForm NewsPostingForm = (NewsPostingfForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newsText", "Введите текст новости.");
        String newsText = NewsPostingForm.getNewsText();

    }
}