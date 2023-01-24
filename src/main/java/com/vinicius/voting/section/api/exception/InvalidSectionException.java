package com.vinicius.voting.section.api.exception;

import org.springframework.http.HttpStatus;

public class InvalidSectionException extends VotingSectionException {
    public InvalidSectionException() {
        super(HttpStatus.BAD_REQUEST, "invalid-section", "Data de fechamento inválida.");
    }
}
