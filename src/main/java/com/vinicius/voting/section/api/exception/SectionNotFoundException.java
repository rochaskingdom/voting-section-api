package com.vinicius.voting.section.api.exception;

import org.springframework.http.HttpStatus;

public class SectionNotFoundException extends VotingSectionException {
    public SectionNotFoundException() {
        super(HttpStatus.NOT_FOUND, "section-not-found", "Sessão não encontrada.");
    }
}

