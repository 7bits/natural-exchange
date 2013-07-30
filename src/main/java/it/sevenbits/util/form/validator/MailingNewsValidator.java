package it.sevenbits.util.form.validator;


import it.sevenbits.util.form.MailingNewsForm;
import it.sevenbits.util.form.NewsPostingfForm;
import org.apache.commons.validator.routines.EmailValidator;
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
public class MailingNewsValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MailingNewsForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MailingNewsForm MailingNewsForm = (MailingNewsForm) target;

        EmailValidator.getInstance().isValid(((MailingNewsForm) target).getEmail());
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Введите e-mail адрес.");
        String newsText = MailingNewsForm.getEmail();

    }
}