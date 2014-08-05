package it.sevenbits.util.form.validator;

import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.User;
import it.sevenbits.util.form.VkEntryEmailForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

/**
 * Created by evgeniy on 8/5/14.
 */
@Component
public class VkEntryEmailFormValidator implements Validator {
    private final static int maxEmailLength = 26;

    @Override
    public boolean supports(final Class<?> clazz) {
        return VkEntryEmailFormValidator.class.isAssignableFrom(clazz);
    }

    @Autowired
    private UserDao userDao;

    @Override
    public void validate(final Object target, final Errors errors) {
        VkEntryEmailForm vkEntryEmailForm = (VkEntryEmailForm) target;
        ValidationUtils.rejectIfEmpty(errors, "email", "email.empty",
            "Пожалуйста, введите свой email.");
        String email = vkEntryEmailForm.getEmail();
        if (email.length() > maxEmailLength) {
            errors.rejectValue("email", "email.tooLong", "Недопустимо больше 26 знаков");
        }
        try {
            User user = this.userDao.findUserByEmail(email);
            errors.rejectValue("email", "email.alreadyExist", "Пользователь с таким email уже существует, пожалуйста, " +
                    "введите другой email");
        } catch (UsernameNotFoundException ex) {

        }
    }
}
