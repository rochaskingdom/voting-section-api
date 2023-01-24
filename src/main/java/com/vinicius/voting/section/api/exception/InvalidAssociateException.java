package com.vinicius.voting.section.api.exception;

import org.springframework.http.HttpStatus;

public class InvalidAssociateException extends VotingSectionException {
    public InvalidAssociateException() {
        super(HttpStatus.BAD_REQUEST, "invalid-associate", "Já existe um associado com esse número de documento");
    }
}
