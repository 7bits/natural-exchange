package it.sevenbits.util.form.validator;

import it.sevenbits.services.parsers.StringParser;
import it.sevenbits.util.FileValidatorConstants;
import it.sevenbits.util.form.validator.validationMethods.CheckingLength;
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
    private final static int MAX_TITLE_LENGTH = 100;
    private final static int MAX_TAGS_LENGTH = 100;
    private final static int MAX_ADVERTISEMENT_TEXT_LENGTH = 500;

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
                errors.rejectValue("image", "image", "Неверный формат файла.");
            } else if (photoFile.getSize() > FileValidatorConstants.MAX_FILE_SIZE) {
                errors.rejectValue("image", "image", "Размер файла не должен превышать 3 мегабайт.");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.empty", "Заполните поле заголовка.");
        String title = advertisementPlacingForm.getTitle();
        String text = advertisementPlacingForm.getText();
        String tags = advertisementPlacingForm.getTags();

        if (tags.length() > MAX_TAGS_LENGTH) {
            errors.rejectValue("tags", "tags.tooLong", "Слишком много тегов у объявления. Пожалуйста, уберите несколько тегов.");
        }
        if (CheckingLength.validateTagsForTooLongTag(tags)) {
            errors.rejectValue("tags", "tags.tooLongTag", "Недопустим тег длиннее 20 символов.");
        }
        if ((title.length()) > MAX_TITLE_LENGTH) {
            errors.rejectValue("title", "title.tooLong", "Недопустимо больше 100 знаков в заголовке.");
        }
        if ((text.length()) > MAX_ADVERTISEMENT_TEXT_LENGTH) {
            errors.rejectValue("text", "text.tooLong", "Недопустимо больше 500 знаков в описании.");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "text", "text.empty", "Описание не должно быть пустым."
        );
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.empty", "Заполните поле заголовка.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "category.empty", "Выберите категорию.");
    }
}