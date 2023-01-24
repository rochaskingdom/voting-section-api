package com.vinicius.voting.section.api.exception;

import org.springframework.http.HttpStatus;

public class AssociateNotFoundException extends VotingSectionException {
    public AssociateNotFoundException() {
        super(HttpStatus.NOT_FOUND, "associate-not-found", "Associado não encontrado.");
    }
}

