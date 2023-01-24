package com.vinicius.voting.section.api.exception;

import org.springframework.http.HttpStatus;

public class VoteNotFoundException extends VotingSectionException {
    public VoteNotFoundException() {
        super(HttpStatus.NOT_FOUND, "vote-not-found", "Voto não encontrada.");
    }
}

