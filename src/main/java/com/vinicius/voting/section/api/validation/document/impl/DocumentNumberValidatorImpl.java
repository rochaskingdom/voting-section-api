package com.vinicius.voting.section.api.validation.document.impl;


import com.vinicius.voting.section.api.annotation.DocumentNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DocumentNumberValidatorImpl implements ConstraintValidator<DocumentNumber, String> {

    private static final CPFValidatorImpl CPF_VALIDATOR = new CPFValidatorImpl();
    private static final CNPJValidatorImpl CNPJ_VALIDATOR = new CNPJValidatorImpl();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return DocumentNumberValidatorImpl.isValidDocumentNumber(value);
    }

    public static boolean isValidDocumentNumber(String value) {
        return CPF_VALIDATOR.isValid(value) || CNPJ_VALIDATOR.isValid(value);
    }
}