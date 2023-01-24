package com.vinicius.voting.section.api.validation.document;

public interface ObjectValidator<T> {
    boolean isValid(final T var1);
}
