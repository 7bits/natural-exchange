package it.sevenbits.util.form.validator;

import it.sevenbits.exceptions.WrongFileParameters;
import it.sevenbits.util.form.EditingUserInfoForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Component
public class EditingUserInfoFormValidator implements Validator {
    private final int MAX_LENGTH = 20;

    private final static int MAX_FILE_SIZE = 3 * 1024 * 1024;

    private Set<String> photoFileTypes;

    EditingUserInfoFormValidator() {
        this.photoFileTypes = new HashSet<>();
        this.photoFileTypes.add("jpg");
        this.photoFileTypes.add("jpeg");
        this.photoFileTypes.add("png");
        this.photoFileTypes.add("img");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return EditingUserInfoForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EditingUserInfoForm editingUserInfoForm = (EditingUserInfoForm) target;
        MultipartFile photoFile = editingUserInfoForm.getImage();

        if (!photoFile.getOriginalFilename().equals("")) {
            String contentType = this.getType(photoFile.getOriginalFilename());
            if (!this.photoFileTypes.contains(contentType)) {
                errors.rejectValue("image", "image", "Неверный формат файла");
            } else if (photoFile.getSize() > MAX_FILE_SIZE) {
                errors.rejectValue("image", "image", "Размер файла не должен превышать 3 мегобайт");
            }
        }

        int firstNameLength = editingUserInfoForm.getFirstName().length();
        int lastNameLength = editingUserInfoForm.getLastName().length();
        if (firstNameLength > MAX_LENGTH) {
            errors.rejectValue("FirstName", "FirstName", "Допускается не более 20 символов");
        } else if (firstNameLength == 0) {
            errors.rejectValue("FirstName", "FirstName", "Пустое имя недопустима");
        }
        if (lastNameLength > MAX_LENGTH) {
            errors.rejectValue("LastName", "LastName", "Допускается не более 20 символов");
        } else if (lastNameLength == 0) {
            errors.rejectValue("LastName", "LastName", "Пустая фамилия недопустимо");
        }
    }

    private String getType(final String fileName) {
        String[] parts = StringUtils.split(fileName, '.');
        int length = parts.length;
        return parts[length - 1];
    }
}
