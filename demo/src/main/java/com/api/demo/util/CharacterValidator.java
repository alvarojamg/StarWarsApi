package com.api.demo.util;


import com.api.demo.util.exception.CommonException;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

import static com.api.demo.util.enums.Errors.ERROR_GENDER;
import static com.api.demo.util.enums.Errors.ERROR_MAX_CHARACTERS;


@Component
@Validated
public class CharacterValidator {

    private final Validator validator;

    public CharacterValidator(Validator validator) {
        this.validator = validator;
    }

    public void validateParams(String name, String gender, String hairColor, String homeWord) {

        if (name != null && name.length() > 50) {
            throw new CommonException(ERROR_MAX_CHARACTERS);
        }

        if (gender != null && !gender.matches("(?i)^(male|female|n/a|none|unknown)?$")) {
            throw new CommonException(ERROR_GENDER);
        }

        if (hairColor != null && hairColor.length() > 30) {
            throw new CommonException(ERROR_MAX_CHARACTERS);
        }

        if (homeWord != null && homeWord.length() > 30) {
            throw new CommonException(ERROR_MAX_CHARACTERS);
        }
    }
}