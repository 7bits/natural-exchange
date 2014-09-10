package it.sevenbits.util.form.validator;

import it.sevenbits.util.form.EditingUserInfoForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EditingUserInfoFormValidator implements Validator {
    private final int MAX_LENGTH = 20;

    @Override
    public boolean supports(Class<?> aClass) {
        return EditingUserInfoForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EditingUserInfoForm editingUserInfoForm = (EditingUserInfoForm) target;
        int firstNameLength = editingUserInfoForm.getFirstName().length();
        int lastNameLength = editingUserInfoForm.getLastName().length();
        if (firstNameLength > MAX_LENGTH) {
            errors.rejectValue("FirstName", "FirstName", "Допускается не более 20 символов");
        } else if (firstNameLength == 0) {
            errors.rejectValue("FirstName", "FirstName", "Пустое имя недопустимо");
        }
        if (lastNameLength > MAX_LENGTH) {
            errors.rejectValue("LastName", "LastName", "Допускается не более 20 символов");
        } else if (lastNameLength == 0) {
            errors.rejectValue("LastName", "LastName", "Пустая фамилия недопустимо");
        }
    }
}
