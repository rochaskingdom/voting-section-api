package com.vinicius.voting.section.api.exception;

import org.springframework.http.HttpStatus;

public class InvalidVoteSectionException extends VotingSectionException {
    public InvalidVoteSectionException() {
        super(HttpStatus.BAD_REQUEST, "invalid-vote-section", "Sessão de votação encerrada.");
    }
}
