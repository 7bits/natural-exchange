package it.sevenbits.util.form.validator.user;

import it.sevenbits.util.form.user.MailingNewsForm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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