package com.borchowiec.userservice.annotation;

import com.borchowiec.userservice.validation.UniqueUsernameValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUsernameValidation.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "Username already taken";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
