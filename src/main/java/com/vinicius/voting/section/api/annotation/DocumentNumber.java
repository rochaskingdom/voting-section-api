package com.vinicius.voting.section.api.annotation;

import com.vinicius.voting.section.api.validation.document.impl.DocumentNumberValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DocumentNumberValidatorImpl.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface DocumentNumber {

    String message() default "Número de documento inválido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
