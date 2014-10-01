package it.sevenbits.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessages {

    public static Map<String, String> getFieldsErrorMessages(final BindingResult bindingResult) {
        Map<String, String> errorMessages = new HashMap<>();
        for(FieldError error : bindingResult.getFieldErrors()) {
            errorMessages.put(error.getField(), error.getDefaultMessage());
        }
        return errorMessages;
    }
}
