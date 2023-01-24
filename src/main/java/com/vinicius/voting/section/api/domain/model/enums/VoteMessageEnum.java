package com.vinicius.voting.section.api.domain.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.vinicius.voting.section.api.exception.InvalidValueException;

import java.util.Arrays;

public enum VoteMessageEnum {

    YES,
    NO;

    @JsonCreator
    static VoteMessageEnum of(String value) throws InvalidValueException {
        try {
            return valueOf(value);
        } catch (Exception e) {
            throw new InvalidValueException("Voto inválido. Os valores permitidos são: " + Arrays.toString(values()) + "");
        }
    }
}
