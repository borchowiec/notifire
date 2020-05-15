package com.borchowiec.userservice.annotation;

import com.borchowiec.userservice.validation.UniqueEmailValidation;
import com.borchowiec.userservice.validation.UniqueUsernameValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Checks if field email is unique.
 */
@Documented
@Constraint(validatedBy = UniqueEmailValidation.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "Email already taken";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
