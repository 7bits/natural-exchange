package it.sevenbits.util.form.validator;


import it.sevenbits.util.form.NewsPostingForm;
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
        return NewsPostingForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NewsPostingForm NewsPostingForm = (it.sevenbits.util.form.NewsPostingForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newsText", "newsText.empty", "Введите текст письма.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newsTitle", "newsTitle.empty", "Введите заголовок.");
        //String newsText = NewsPostingForm.getNewsText();

    }
}