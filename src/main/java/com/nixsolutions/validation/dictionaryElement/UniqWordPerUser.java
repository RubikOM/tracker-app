package com.nixsolutions.validation.dictionaryElement;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = WordUniqValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface UniqWordPerUser {
    String message() default "You already created this word";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
