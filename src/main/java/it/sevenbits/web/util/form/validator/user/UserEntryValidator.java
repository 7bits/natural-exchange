package it.sevenbits.web.util.form.validator.user;

import it.sevenbits.repository.dao.UserDao;
import it.sevenbits.web.util.form.user.UserEntryForm;
import it.sevenbits.web.util.form.user.UserRegistrationForm;
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
        UserEntryForm userEntryForm = (UserEntryForm) target;
        String password = userEntryForm.getPassword();
        String email = userEntryForm.getEmail();
        if (!EmailValidator.getInstance().isValid(((UserEntryForm) target).getEmail())) {
            errors.rejectValue("email", "email.not.correct", "Введите корректный e-mail адрес.");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "Пароль не может быть пустым.");
    }
}




