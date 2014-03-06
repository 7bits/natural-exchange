package it.sevenbits.util.form.validator;

import it.sevenbits.dao.UserDao;
import it.sevenbits.util.form.UserRegistrationForm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: sevenbits
 * Date: 7/29/13
 * Time: 3:21 PM
 *
 */
@Component
public class UserEditProfileValidator implements Validator {
    @Resource(name = "userDao")
    private UserDao userDao;


    @Override
    public boolean supports(final Class<?> clazz) {
        return UserRegistrationForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        UserRegistrationForm userRegistrationForm = (UserRegistrationForm) target;
        String password = userRegistrationForm.getPassword();
        String email = userRegistrationForm.getEmail();
        if (!EmailValidator.getInstance().isValid(((UserRegistrationForm) target).getEmail())) {
            errors.rejectValue("email", "email.not.correct", "Введите корректный e-mail адрес.");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "Пароль не может быть пустым.");
      //  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.not.empty", "Введите Ваше имя.");
       // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.not.empty", "Введите Вашу фамилию.");
    }
}




