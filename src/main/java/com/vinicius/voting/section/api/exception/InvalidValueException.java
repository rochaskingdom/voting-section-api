package com.vinicius.voting.section.api.exception;

import org.springframework.http.HttpStatus;

public class InvalidValueException extends VotingSectionException {

    public InvalidValueException(String description) {
        super(HttpStatus.BAD_REQUEST, "invalid_value", description);
    }

}
