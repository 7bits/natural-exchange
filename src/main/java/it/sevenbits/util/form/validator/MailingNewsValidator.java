package it.sevenbits.util.form.validator;

import it.sevenbits.util.form.MailingNewsForm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
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
    public boolean supports(final Class<?> clazz) {
        return MailingNewsForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        if (!EmailValidator.getInstance().isValid(((MailingNewsForm) target).getEmailNews())) {
             errors.rejectValue("emailNews", "email.not.correct", "Введите корректный e-mail адрес.");
        }
    }
}