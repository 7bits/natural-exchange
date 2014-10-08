package it.sevenbits.util.form.validator;

import it.sevenbits.services.parsers.StringParser;
import it.sevenbits.util.FileValidatorConstants;
import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import it.sevenbits.util.form.AdvertisementPlacingForm;
import org.springframework.web.multipart.MultipartFile;

/**
 * Validator for advertisement placing form
 */
@Component
public class AdvertisementPlacingValidator implements Validator {
    private final static int maxTitleLength = 100;
    private final static int maxAdvertisementTextLength = 500;

    @Override
    public boolean supports(final Class<?> clazz) {
        return AdvertisementPlacingForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        AdvertisementPlacingForm advertisementPlacingForm = (AdvertisementPlacingForm) target;
        MultipartFile photoFile = advertisementPlacingForm.getImage();

        if (!photoFile.getOriginalFilename().equals("")) {
            String contentType = StringParser.getType(photoFile.getOriginalFilename());
            if (!FileValidatorConstants.photoFileTypes.contains(contentType)) {
                errors.rejectValue("image", "image", "Неверный формат файла");
            } else if (photoFile.getSize() > FileValidatorConstants.MAX_FILE_SIZE) {
                errors.rejectValue("image", "image", "Размер файла не должен превышать 3 мегабайт");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.empty", "Заполните поле заголовка.");
        String title = advertisementPlacingForm.getTitle();
        String text = advertisementPlacingForm.getText();
        if ((title.length()) > maxTitleLength) {
            errors.rejectValue("title", "title.tooLong", "Недопустимо больше 100 знаков в заголовке.");
        }
        if ((text.length()) > maxAdvertisementTextLength) {
            errors.rejectValue("text", "text.tooLong", "Недопустимо больше 500 знаков в описании.");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "text", "text.empty", "Описание не должно быть пустым."
        );

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "category.empty", "Выберите категорию.");
    }


}