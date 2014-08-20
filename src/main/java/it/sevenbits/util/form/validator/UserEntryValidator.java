package it.sevenbits.util.form.validator;

import it.sevenbits.dao.UserDao;
import it.sevenbits.util.form.UserEntryForm;
import it.sevenbits.util.form.UserRegistrationForm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

@Component
public class UserEntryValidator implements Validator {
    @Resource(name = "userDao")
    private UserDao userDao;


    @Override
    public boolean supports(final Class<?> clazz) {
        return UserRegistrationForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        UserEntryForm userEntryForm = (it.sevenbits.util.form.UserEntryForm) target;
        String password = userEntryForm.getPassword();
        String email = userEntryForm.getEmail();
        if (!EmailValidator.getInstance().isValid(((UserEntryForm) target).getEmail())) {
            errors.rejectValue("email", "email.not.correct", "Введите корректный e-mail адрес.");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "Пароль не может быть пустым.");
    }
}




