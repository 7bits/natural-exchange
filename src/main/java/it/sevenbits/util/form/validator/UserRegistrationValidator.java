package it.sevenbits.util.form.validator;

import it.sevenbits.util.form.MailingNewsForm;
import it.sevenbits.util.form.UserRegistrationForm;
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
public class UserRegistrationValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return UserRegistrationForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        UserRegistrationForm userRegistrationForm = (it.sevenbits.util.form.UserRegistrationForm) target;
        String password = userRegistrationForm.getPassword();
        String confirmPassword = userRegistrationForm.getConfirmPassword();
        if (!EmailValidator.getInstance().isValid(((UserRegistrationForm) target).getEmail())) {
            errors.rejectValue("email", "email.not.correct", "Введите корректный e-mail адрес.");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.empty", "Введите Ваше имя, пожалуйста.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.empty", "Введите Вашу фамилию, пожалуйста.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "Пароль не может быть пустым.");
        if (!password.equals(confirmPassword)) {
            errors.rejectValue("confirmPassword", "password.not.correct", "Пароль не подтвержден, повторите.");
        }
    }
}




