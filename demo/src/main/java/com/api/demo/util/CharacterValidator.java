package com.api.demo.util;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@Validated
public class CharacterValidator {

    private final Validator validator;

    public CharacterValidator(Validator validator) {
        this.validator = validator;
    }

    public void validateSearchParams(
            String name,
            String homeworld,
            String gender,
            String hairColor
    ) {
        SearchParams params = new SearchParams(name, gender, hairColor);
        Set<ConstraintViolation<SearchParams>> violations = validator.validate(params);

        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("Error: " + errorMessage);
        }
    }
    private record SearchParams(
            @Size(max = 50, message = "Characters out of limit")
            String name,

            @Pattern(regexp = "^(male|female|n/a|none|unknown)?$",
                    flags = Pattern.Flag.CASE_INSENSITIVE,
                    message = "Invalid gender")
            String gender,

            @Size(max = 30, message = "Characters out of limit")
            String hairColor
    ) {}

    public void validatePagination(int page, int limit) {
        if (page < 1 || page > 8) {
            throw new IllegalArgumentException("Page limit is  8");
        }
        if (limit < 1 || limit > 8) {
            throw new IllegalArgumentException("Limit have to be between 1 and 8");
        }
    }
}