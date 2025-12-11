package com.mvc.mvc.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordValidation {
    String message() default "Password must contain: one uppercase letter, one lowercase letter, one special char, and be at least 10 characters long.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
