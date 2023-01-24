package com.vinicius.voting.section.api.exception;

import org.springframework.http.HttpStatus;

public class AgendaNotFoundException extends VotingSectionException {
    public AgendaNotFoundException() {
        super(HttpStatus.NOT_FOUND, "agenda-not-found", "Agenda não encontrado.");
    }
}

