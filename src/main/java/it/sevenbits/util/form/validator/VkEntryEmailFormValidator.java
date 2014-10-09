package it.sevenbits.util.form.validator;

import it.sevenbits.dao.UserDao;
import it.sevenbits.entity.User;
import it.sevenbits.util.form.VkEntryEmailForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(VkEntryEmailFormValidator.class);

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
            errors.rejectValue("email", "email.tooLong", "Email не может быть длиннее 26 знаков.");
        }
        if (!email.contains(".")) {
            errors.rejectValue("email", "email.invalid", "Email должен содержать точку.");
        }
        try {
            User user = this.userDao.findUserByEmail(email);
            errors.rejectValue("email", "email.alreadyExist", "Пользователь с таким email уже существует, пожалуйста, " +
                    "введите другой email.");
        } catch (UsernameNotFoundException ex) {
            logger.warn("User doesn't found");
        }
    }
}
