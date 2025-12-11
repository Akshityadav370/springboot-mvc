package com.mvc.mvc.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValidation, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null)
            return false;

        boolean hasUppercase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowercase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasSpecial = password.matches(".*[^a-zA-Z0-9].*");
        boolean minLength = password.length() >= 10;

        return hasUppercase && hasLowercase && hasSpecial && minLength;
    }
}
